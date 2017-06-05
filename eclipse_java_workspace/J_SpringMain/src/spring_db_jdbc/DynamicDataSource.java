package spring_db_jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return "testDataSource";   //这是可以动态选择JDBC数据库的
        //return "dataSource"; 
    }
}