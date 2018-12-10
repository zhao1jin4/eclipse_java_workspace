package mybatis_spring;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
public class DynamicDataSource extends AbstractRoutingDataSource 
{

    public static final String DB_LOCAL="dataSourceLocal";
    public static final String DB_ENC="dataSourceEnc";
    public static final String DB_DRUID="dataSourceDruid";
    
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDBType(String dbType) {
        contextHolder.set(dbType);
    }
 
    
    @Override
    protected Object determineCurrentLookupKey() {
        return contextHolder.get();
    }
}