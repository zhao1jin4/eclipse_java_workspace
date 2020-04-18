package dynamic_sql.simple;


import static dynamic_sql.simple.SimpleTableDynamicSqlSupport.birthDate;
import static dynamic_sql.simple.SimpleTableDynamicSqlSupport.employed;
import static dynamic_sql.simple.SimpleTableDynamicSqlSupport.firstName;
import static dynamic_sql.simple.SimpleTableDynamicSqlSupport.id;
import static dynamic_sql.simple.SimpleTableDynamicSqlSupport.lastName;
import static dynamic_sql.simple.SimpleTableDynamicSqlSupport.occupation;
import static dynamic_sql.simple.SimpleTableDynamicSqlSupport.simpleTable;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isNull;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
public class DynamicSimpleDaoMain
{
	public static void main(String... args) throws Exception
	{
		selectSimpleDao();
    }
	public static void selectSimpleDao( ) throws Exception
	{
    	String resource = "dynamic_sql/simple/Configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);//Properties,Enviroment
		sqlSessionFactory.getConfiguration().addMapper(SimpleTableAnnotatedMapper.class);//加带Annotation的类
    	
		//---
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SimpleTableAnnotatedMapper mapper = session.getMapper(SimpleTableAnnotatedMapper.class);
           
            //-----select single
            //SimpleTableDynamicSqlSupport.*;
            //org.mybatis.dynamic.sql.SqlBuilder.*;
            SelectStatementProvider selectStatement = select(id.as("A_ID"), firstName, lastName, birthDate, employed, occupation)
                    .from(simpleTable)
                    .where(id, isEqualTo(1))
                    .or(occupation, isNull())
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
            List<SimpleTableRecord> rows = mapper.selectMany(selectStatement);
           
            /*
            List<SimpleTableRecord> rows = mapper.selectMany(new  SelectStatementProvider() {
				@Override
				public Map<String, Object> getParameters()
				{
					Map<String, Object> param=new HashMap< >(); 
					param.put("id", 1);
					return param;
				}
				@Override
				public String getSelectStatement()
				{
					//所有参数要以parameters.开头
					return "select * from SimpleTable where id=#{parameters.id}";
				}
			}); */
            System.out.println(rows);
            
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
    } 
}
