package mybatis_spring;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
public class DynamicDataSource extends AbstractRoutingDataSource 
{

    public static final String DBLOCAL="dataSourceLocal";
    public static final String DBREMOTE="dataSourceRemote";
    
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDBType(String dbType) {
        contextHolder.set(dbType);
    }
 
    
    @Override
    protected Object determineCurrentLookupKey() {
        return contextHolder.get();
    }
}