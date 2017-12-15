package mybatis_annotation;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

//@CacheNamespace(size = 512)
public interface JobDao {
	//有时要 Job implements Serializable
	@Select("select * from job_history where user_Id=#{_id}")
	@Results(value = {
			@Result(property="requirement", column="job_requirement",
					javaType=java.util.List.class,jdbcType=JdbcType.VARCHAR,typeHandler=MyXMLTypeHandler.class),
			@Result(property="jobTitle", column="job_title" )
	   })
	public List<Job> getJobsByUserId(@Param("_id") int userid);
	
	
	@Select("select * from job_history where job_id=#{_id}")
	@Results(value = {
			@Result(property="requirement", column="job_requirement",
					javaType=java.util.List.class,jdbcType=JdbcType.VARCHAR,typeHandler=MyXMLTypeHandler.class),
			@Result(property="jobTitle", column="job_title" )
	   })
	public List<Job> getJobsByPK(@Param("_id") int jobId);
	
	
	
	@Select("select * from job_history where start_date = #{startDate}")  
	@Results(value = {
			@Result(property="requirement", column="job_requirement",
					javaType=java.util.List.class,jdbcType=JdbcType.VARCHAR,typeHandler=MyXMLTypeHandler.class),
			@Result(property="jobTitle", column="job_title" )
	   })
	//如oracle 是date 类型,使用java.sq.Date 会使用索引,而util.Date不会 !!!
	//如 oracle 是 timestamp 类型无论是util.Date还是sql.Date都使用索引
	 //而MySQL可以直接传字串就OK
	public List<Job> getJobByStartDate(@Param("startDate") java.util.Date startDate);
	
	
	@Insert("insert into job_history(job_requirement,job_title,user_id) values(#{requirement,javaType=java.util.List,jdbcType=VARCHAR,typeHandler=mybatis_annotation.MyXMLTypeHandler},#{jobTitle},1)")
	public void saveJob(Job job);
   
	
    //----以下没测试过
    @Select("select job_id,job_title,user_Id from job_history where job_title like 'java%'")
    @Results({
		 @Result(property="id",     column="job_id"),
		 @Result(property="jobTitle",     column="job_title"),
		 @Result(property="user", column="user_Id",  javaType=User.class, // @One 时column 是外键
		 			one=@One(select="mybatis_annotation.UserDao.getUserById"))
		 })
    public Job getJobAssociationUser();
	  
    
	@Select("select * from job_history where user_Id=#{userId}")
	@Results(value = {
			@Result(property="requirement", column="job_requirement",
					javaType=java.util.List.class,jdbcType=JdbcType.VARCHAR,typeHandler=MyXMLTypeHandler.class),
			@Result(property="jobTitle", column="job_title" )
	   })
	public List<Job> getJobsByUser(int userId);//@Param("userId") 
	
  //------ 测试中
	
	/*
   public void deleteJobsByUsername(String username);
   public void updateJobsByUsername(String username,Job job);
   */
}