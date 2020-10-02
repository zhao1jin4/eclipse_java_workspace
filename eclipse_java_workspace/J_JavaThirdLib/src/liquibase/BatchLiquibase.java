package liquibase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.StandardChangeLogHistoryService;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.RollbackImpossibleException;
import liquibase.resource.ClassLoaderResourceAccessor;


public class BatchLiquibase {

	public static void main(String[] args) throws Exception {
		//spring boot 的 DataSourceBuilder 优先使用hikari
		DataSource datasource=DataSourceBuilder.create().url("").username("").password("").build();
		batchUpdate(datasource);
	}
	public static void batchUpdate(DataSource datasource) throws Exception {
		Connection connection=datasource.getConnection();
		JdbcConnection jdbcConnection=new JdbcConnection(connection);
		Database database=DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
		database.setDatabaseChangeLogTableName("T_CHANGE_LOG");
		database.setDatabaseChangeLogLockTableName("T_LOCK_CHANGE_LOG");

		if(!database.getRanChangeSetList().isEmpty())
		{
			StandardChangeLogHistoryService service=new StandardChangeLogHistoryService();
			List<Map<String, ?>> changeLogTable = service.queryDatabaseChangeLogTable(database);

			List<String> changeLogs=new ArrayList<>();
			changeLogTable.stream().forEach(o->changeLogs.add(o.get("FILENAME").toString()));
			
			List<String> updates=new ArrayList<>();
			//spring的类
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource  resouces[]=resolver.getResources("classpath*:db/sql/*.sql");
			for(Resource resource:resouces) 
			{
				String fileName="db/sql/"+resource.getFilename();
				if(!changeLogs.contains(fileName))
					updates.add(fileName);
			}
			Collections.sort(updates);
			for(String update:updates)
			{
				boolean rollback=true;
				Liquibase liquibase=new Liquibase(update,new ClassLoaderResourceAccessor(),database);
				System.out.println(update+"文件的SQL");
				//为打日志
				for(ChangeSet changeSet:liquibase.getDatabaseChangeLog().getChangeSets())
				{
					rollback=rollback & changeSet.supportsRollback(database);
					changeSet.getChanges().forEach(change->{
						Arrays.stream(change.generateStatements(database)).forEach(
									s -> {
										System.out.println("SQL-"+s.toString());
									}
								);
						try {
							Arrays.stream(change.generateRollbackStatements(database)).forEach(
									s ->{
										System.out.println("Rollback SQL-"+s.toString());
									}
								);
						} catch (RollbackImpossibleException e) {
							System.out.println("Rollback SQL Fail");
						}
					});
				}
				if(rollback) {
					liquibase.updateTestingRollback(null);
				}else
				{
					liquibase.update((String)null);
				}
				liquibase.close();
			}
			database.commit();
		}
	}
}
