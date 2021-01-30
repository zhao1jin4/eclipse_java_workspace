package mybatis.dao;

import java.util.List;

import mybatis.vo.User;

  
public interface UserMapper {
	 
	public List<User> selectAll();
	public User selectById(int id);  
}
