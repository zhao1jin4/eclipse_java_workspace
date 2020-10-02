package mybatis_annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.domain.PageRequest;

import mybatis_spring.Employee;
public class SQLLogPageTotal {
	
	PageRequest page=new PageRequest(2,20);
	  SqlSessionFactory sessionFactory;
	public SqlSessionTemplate sqlSessionTemplate;
	public SQLLogPageTotal(SqlSessionTemplate sqlSessionTemplate)
	{
	 
		page.getOffset();
		page.getPageSize();
		this.sqlSessionTemplate=sqlSessionTemplate;
		sessionFactory=sqlSessionTemplate.getSqlSessionFactory();
	}
	public List<Employee> getList(String mapperId,Object param,RowBounds rowBounds)
	{
		

//		Map<String,Object> param=new HashMap<>();
//		param.put("username", "li");
//		String mapperId="org.zh.mybatis.ns.queryAllEmployeeByPage";
//		RowBounds rowBounds=new RowBounds(offset,limit);//对于MySQL其实就是limit 对于查询中有 <collection> 是不准的
		//-------------
		Configuration  configuration=sessionFactory.getConfiguration();
		Collection<String> stateNames=configuration.getMappedStatementNames();
		MappedStatement mapStatment=configuration.getMappedStatement(mapperId);
	
		BoundSql boundSql=mapStatment.getBoundSql(param);
		String sql=boundSql.getSql();
		System.out.println("sql=>"+sql);//里面没有limit,使用 RowBounds
		String countSql="select count(*) from ("+sql+") A";
		System.out.println("count sql=>"+countSql);
		
		List<ParameterMapping> list=boundSql.getParameterMappings(); 
		MetaObject metaObject=configuration.newMetaObject(param);
		Map<String,Object> sqlParam=new HashMap<>();
		for(ParameterMapping parameterMapping :list)
		{
			Object value=metaObject.getValue(parameterMapping.getProperty());
			sqlParam.put(parameterMapping.getProperty(),value);
		}
		System.out.println("parm=>"+sqlParam);
		
		//org.apache.ibatis.annotations.ResultMap
		List<org.apache.ibatis.mapping.ResultMap> results=mapStatment.getResultMaps();
		if(results!=null&&results.size()>0)
		{
			Class<?> resClass=results.get(0).getType();
			System.out.println("resClass= "+resClass);
		}
		
		/*对应于配置 
		  <typeHandlers>
        		<typeHandler>
		 */
		TypeHandlerRegistry typeHandlerRegistry=	configuration.getTypeHandlerRegistry();
		boolean isHave=typeHandlerRegistry.hasTypeHandler(param.getClass());
		//<T>  
		List<Employee> res=sqlSessionTemplate.selectList(mapperId, param, rowBounds);
		return res;
		
		
	}
}
