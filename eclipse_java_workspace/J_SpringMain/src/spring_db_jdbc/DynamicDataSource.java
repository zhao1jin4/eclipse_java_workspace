package spring_db_jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return "testDataSource";   //���ǿ��Զ�̬ѡ��JDBC���ݿ��
        //return "dataSource"; 
    }
}