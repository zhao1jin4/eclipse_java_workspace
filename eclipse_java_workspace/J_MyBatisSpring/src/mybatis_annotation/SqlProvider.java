package mybatis_annotation;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
public class SqlProvider
{
	
	 public String getRecordCountByMap(Map<String,String> params)
	 {
		 SQL sql=new SQL();
		 sql.SELECT("count(*)")
		 .FROM("USER");
		 if(params.get("userName")!=null)
		 {
			 sql.WHERE(" USER_NAME=#{userName}");//,javaType=string,jdbcType=VARCHAR
		 }
		 if(params.get("password")!=null)
		 {
			 sql.WHERE(" password=#{password}");//,javaType=string,jdbcType=VARCHAR
		 }
		 //多个WHERE 是(..  and .. )
		 return sql.toString();
		 //----
//		 return new SQL() {{
//			 SELECT("count(*)");
//			 FROM("USER");
//			 }}.toString();
	 }
	 public String   queryByPage (Map<String,Object> params) //Oracle,H2
	 {
		 SQL sql=new SQL();
		 sql.SELECT( "USERID ,USER_NAME ");
//		 if(params.get("userName")!=null)
//		 {
//			 sql.FROM("( select tmp.*, rownum row_num from USER  tmp   where  rownum  <= #{end}   and USER_NAME = #{userName}  ) page");
//		 }else
//		 {
//			 sql.FROM("( select tmp.*, rownum row_num from USER  tmp   where  rownum  <= #{end} ) page");
//		 }
//		 sql.WHERE("page.row_num >  #{start}" ) ;
//		 return sql.toString();
				 
		 //如果是MySQL  limit 2,1 从第二行开始(0是第一行),查一行
		 sql.FROM("USER");
		 if(params.get("userName")!=null)
		 {
			 sql.FROM(" USER_NAME = #{userName} ");
		 }
		 if(params.get("password")!=null)
		 {
			 sql.WHERE(" password=#{password}");
		 }
		 return  sql.toString()+ (" limit #{start} , #{len}" ) ;//不能传String类型
	 }
	 
	 
	public String getByUserName(final Map<String, Object> parameters)//加参数
	{
		return new SQL()
		{{
	        SELECT("USER_NAME, PASSWORD,COMMENT");  
	        FROM("USER");  
	        
	        String username = (String) parameters.get("_username"); //可判断是否传值
	        if (username != null) {  
	            WHERE("user_name = #{_username,javaType=string,jdbcType=VARCHAR}");  
	        }  
		}}.toString();
	}
	public String getAll()
	{
		return new SQL()
		{{
	        SELECT("USER_NAME, PASSWORD,COMMENT");  
	        FROM("USER");  
		}}.toString(); 
	}
	
	public String getCount()
	{
		return new SQL()
		{{
	        SELECT("count(*)");  
	        FROM("USER");  
		}}.toString();  
	}
	public String insertSql()
	{
		return new SQL()
		{{
	        INSERT_INTO("USER");  
	        VALUES("userId", "#{testUser.userId}");  //,javaType=int,jdbcType=NUMERIC
	        VALUES("USER_NAME", "#{testUser.userName,javaType=string,jdbcType=VARCHAR}");  
	        VALUES("PASSWORD", "#{testUser.password,javaType=string,jdbcType=VARCHAR}");
	        VALUES("COMMENT", "#{testUser.comment,javaType=string,jdbcType=VARCHAR}"); 
		}}.toString();  
	}
	
	
	public String updateSql()
	{
		return new SQL()
		{{
	        UPDATE("USER");  
	        SET("PASSWORD = #{testUser.password,javaType=string,jdbcType=VARCHAR}");
	        SET("COMMENT = #{testUser.comment,javaType=string,jdbcType=VARCHAR}");  
	        WHERE("USER_NAME = #{testUser.userName,javaType=string,jdbcType=VARCHAR}");  
	    }}.toString();    
	}
	public String deleteSql()
	{
		return new SQL()
		{{ 
	        DELETE_FROM("USER");  
	        WHERE("USER_NAME = #{_username,javaType=string,jdbcType=VARCHAR}");  
		 }}.toString(); 
	}
}
