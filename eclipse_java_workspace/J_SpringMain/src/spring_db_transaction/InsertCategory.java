package spring_db_transaction;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertCategory extends SqlUpdate
{
	public InsertCategory(DataSource datasource)
	{
		this.setDataSource(datasource);
		this.setSql("insert into category values(?,?)");
		this.declareParameter(new SqlParameter(Types.INTEGER));
		this.declareParameter(new SqlParameter(Types.VARBINARY));
		this.compile();
	}
	public int save(Category cate)
	{
		Object [] o=new Object[]{cate.getId(),cate.getName()};
		return this.update(o);
	}

}
