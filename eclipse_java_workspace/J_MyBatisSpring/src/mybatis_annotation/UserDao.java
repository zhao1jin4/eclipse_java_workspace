package mybatis_annotation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.Alias;

//@CacheNamespace(size = 512) //定义在该命名空间内允许使用内置缓存
public interface UserDao {
	
	//MySQL
	@SelectKey(statement="select cast(rand()*100 as SIGNED )", keyProperty="userId", before= true, resultType= int.class)//call identity()
    @Insert("insert into user(userId,user_Name,password,comment) values(#{userId},#{userName},#{password},#{comment})")//参数是User类,#{}中的类的属性名
    public int insert(User user123);
    
    
    @Update("update user set user_Name=#{userName},password=#{password},comment=#{comment} where user_Name=#{userName}")
    public int update(User user123);
    
    
    @Delete("delete from user where user_Name=#{userName}")//一个#{}并且一个参数时,自动对应
    public int delete(String userName123);
    
    
    @Delete("delete from user where user_Name=#{userName} and password=#{password}") //多个参数的对应方法
    public int deleteByTwo(@Param("userName")String userName123,@Param("password")String password123);//不能使用同名重载方法的方式
    
    
    //@Select("select user_Name as userName ,PASSWORD,COMMENT from user order by user_Name asc") //别名要和类的属性名一样
    @Select("select * from user order by user_Name asc")
    @Results(value = {
		@Result(property="userName", column="user_Name")//对使用select *的方式,只配置不相同
   })
    @Options(useCache = false,flushCache = true)//默认是有cache的(只配置 useCache = false无用的),与配置cacheEnabled无关
    public List<User> selectAll();
    
    
    @Select("select count(*) c from user;")
    public int countAll();
    
    
    @Select("select * from user where user_Name=#{userName}")
    @Results(value = {
    		@Result(property="userName", column="user_Name")
       })
    public User findByUserName(String userName123);
    
//-------provider
    @SelectProvider(type = SqlProvider.class, method = "getCount")
    @Options(useCache = true, flushCache = false, timeout = 10000)//flushCache = false表示下次查询时不刷新缓存
    public int providerGetCount();
   
    
    @SelectProvider(type = SqlProvider.class, method = "getByUserName")  
    @Options(useCache = true, flushCache = false, timeout = 10000)
    @Results(value = {
  		@Result(property="userName", column="user_Name")
    })
    public  User providerGetByUserName(@Param("_username") String username);
    
    
    @SelectProvider(type = SqlProvider.class, method = "getAll")
    @Results(value = {
    		@Result(property="userName", column="user_Name")
       })
    public  List<User> providerGetAll();//要 User implements Serializable
    
    
    @InsertProvider(type = SqlProvider.class, method = "insertSql")  
    @SelectKey(statement="select cast(rand()*100 as SIGNED )", keyProperty="testUser.userId", before= true, resultType= int.class)
    public void  providerAddData(@Param("testUser")User u);
  
    
    @UpdateProvider(type = SqlProvider.class, method = "updateSql")  
    int providerUpadteData(@Param("testUser") User testUser);
     
    
    @DeleteProvider(type = SqlProvider.class, method = "deleteSql")  
    public void providerDelete(@Param("_username") String username);  
   
    //--分页 ,Map 参数
    @SelectProvider(type = SqlProvider.class, method = "getRecordCountByMap")
    public long getRecordCountByMap(Map<String,String> params);
    
    @SelectProvider(type = SqlProvider.class, method = "queryByPage")
    @Results(value = {
    		@Result(property="userName", column="user_Name")
       })
    public  List<User>  queryByPage(Map<String,Object> params);
    
  

    @Select("select u.user_name ,count(*) as works from  user u left join job_history j on u.userId=j.user_id group by u.user_name  having u.user_name=#{_username}")
    @Results(value = {
    		@Result(property="username", column="user_name"),
    		@Result(property="works", column="works")
       })
   // @ResultMap(value = "joinData")
    public  JoinData getWorks(@Param("_username") String username);//@Alias("joinData")未成功???
  
    
    //----以下没测试过
//    String INSERT_CANDIDATE = "INSERT INTO candidate (" +
//    		32	            " personId,addressId,employerId,clientId,basic,ote,met," +
//    		33	            " reference,exclusive,createdOn,createdBy,active," +
//    		34	            " priority,code,offers,referredBy,statusId,salCurrencyId,salTenureId) " +
//    		35	          "VALUES " +
//    		
//    String GET_CANDIDATE_BY_ID="select c.* from candidate c where id=#{id} and active=1";  
//    @Select(GET_CANDIDATE_BY_ID)
//	@Results({
//			 @Result(property="id",     column="id"),
//			 @Result(property="person", column="personId",   javaType=PersonVO.class, one=@One(select="com.examples.dao.PersonDAO.doSelectPerson")),
//			 @Result(property="address",column="addressId",  javaType=AddressVO.class, one=@One(select="com.examples.dao.AddressDAO.doSelectAddress")),
//			 @Result(property="sectors", column="id", 		  javaType=List.class, 		many=@Many(select = "com.examples.dao.SectorDAO.doSelectSectorsByCandidate"))
//			 })
//	public CandidateVO doSelectCandidateById(long candidateId);
    
//    String GET_ADDRESS_BY_ID = "SELECT * FROM vw_address WHERE id = #{addressId}";
//    @Select(GET_ADDRESS_BY_ID)
//    21	    @Options(useCache=true)
//    22	    public AddressVO doSelectAddress(long addressId) throws Exception;  
//    
    
//     "INSERT into address (building,street,location,town,postCode,countyId,countryId,notes,createdOn,createdBy,active) VALUES (#{building},#{street},#{location},#{town},#{postCode},#{countyId},#{countryId},#{notes},sysdate(),#{createdBy},1)";
//    
    @Select("select userId,user_Name,password,comment from user where user_Name='lisi'")
	@Results({
			 @Result(property="user_Name",     column="userName"),
			 @Result(property="jobs", column="userId",  javaType=List.class, //--@Many时column 主键
			 			many=@Many(select = "mybatis_annotation.JobDao.getJobsByUser"))
			 })
    public User getUserCollectionJobs();
    
    
    @Select("select * from user where userId=#{user_Id}")
    @Results(value = {
		@Result(property="userName", column="user_Name") 
   })
    public  User getUserById(int user_Id);//@Param("user_Id")
    
	//----以下没用过
	//@ResultMap(value = "myMap")
    //@Alias("joinData")
	//---
}