
package mybatis_annotation;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

/* 
 //也可在 配置log4j
# MyBatis 输出日志 
log4j.logger.org.apache.ibatis=debug,stdout 
log4j.logger.java.sql=debug,stdout
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class SQLLogInterceptor implements Interceptor 
{
    public Object intercept(Invocation invocation) throws Exception
    {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            BoundSql boundSql =statementHandler.getBoundSql();
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
System.out.println("sql:==>"+boundSql.getSql());
            StringBuffer param = new StringBuffer(400);
            param.append("[");
            
            //--为 @ 配置
            //BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
            //MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
            
            Field fieldDel =statementHandler.getClass().getDeclaredField("delegate");
            fieldDel.setAccessible(true);
            BaseStatementHandler delegate = (BaseStatementHandler) fieldDel.get(statementHandler);
            fieldDel.setAccessible(false);
            //
            Field fieldMap =delegate.getClass().getSuperclass().getDeclaredField("mappedStatement");
            fieldMap.setAccessible(true);
            MappedStatement mappedStatement = (MappedStatement) fieldMap.get(delegate);
            fieldMap.setAccessible(false);
            //
            
            Configuration configuration = mappedStatement.getConfiguration();
           //TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();//no use
           //上为了得到 Configuration
            
            Object parameterObject = boundSql.getParameterObject();
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            //--上 为 @ 配置
            if(parameterMappings.size()==0)
            {
            	return invocation.proceed();
            }
            for (int i = 0; i < parameterMappings.size(); i++) 
            {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                String propertyName = parameterMapping.getProperty();
                
                Object value =null;
                if (boundSql.hasAdditionalParameter(propertyName))
                {
                    value = boundSql.getAdditionalParameter(propertyName); 
                }else
                {
                	value=metaObject.getValue(propertyName); //对使用了Spring集成的
                	if(value instanceof char[])//对parameterType="int"或者"string"配置中用#{value},要么是Spring的MapperFactoryBean的一个参数类型为int,String的情况
                	{
                	    char[] valueArray=(char[])value;
                	    value=new String(valueArray);
                	}
                }
                param.append(propertyName).append(":").append(value).append(",");
            }
            param.deleteCharAt(param.lastIndexOf(","));
            param.append("]");
System.out.println("sql param:==>"+param.toString());
            return invocation.proceed();
    }
	@Override
	public Object plugin(Object target) {
		 return Plugin.wrap(target, this);
	}
	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub
		
	}
}



/*

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class SQLLogInterceptor implements Interceptor {
    // 日志对象
    protected Logger log = LoggerFactory.getLogger("paff.sql");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");

            if (delegate != null) {

                MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");

                BoundSql boundSql = delegate.getBoundSql();
                Object parameterObject = boundSql.getParameterObject();

                StringBuffer sb = new StringBuffer(400);

                sb.append("sql:====>\r\n");
                sb.append(removeBreakingWhitespace(boundSql.getSql()));
                sb.append("\r\n");

                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                if (parameterMappings != null) {
                    Configuration configuration = mappedStatement.getConfiguration();
                    TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
                    MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
                    for (int i = 0; i < parameterMappings.size(); i++) {
                        ParameterMapping parameterMapping = parameterMappings.get(i);
                        if (parameterMapping.getMode() != ParameterMode.OUT) {
                            Object value;
                            String propertyName = parameterMapping.getProperty();
                            PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                            if (parameterObject == null) {
                                value = null;
                            } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                                value = parameterObject;
                            } else if (boundSql.hasAdditionalParameter(propertyName)) {
                                value = boundSql.getAdditionalParameter(propertyName);
                            } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
                                value = boundSql.getAdditionalParameter(prop.getName());
                                if (value != null) {
                                    value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                                }
                            } else {
                                value = metaObject == null ? null : metaObject.getValue(propertyName);//----
                            }

                            sb.append("[");
                            sb.append(propertyName);
                            sb.append(":");
                            sb.append(value);
                            sb.append("]");

                        }
                    }
                }
                sb.append("==================================");
                log.info(sb.toString());
            } else {
                log.warn("检测是否重复配置SQLLogInterceptor");
            }
        } catch (Exception e) {
            log.error("未知异常!" + e.getMessage(), e);
        }
        return invocation.proceed();
    }

    protected String removeBreakingWhitespace(String original) {
        StringTokenizer whitespaceStripper = new StringTokenizer(original);
        StringBuilder builder = new StringBuilder();
        for (; whitespaceStripper.hasMoreTokens(); builder.append(" "))
            builder.append(whitespaceStripper.nextToken());

        return builder.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties arg0) {
        // TODO Auto-generated method stub

    }

}
 */