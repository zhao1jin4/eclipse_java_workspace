package mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 

import mybatis.dao.UserMapper;
import mybatis.vo.User;

@RestController
public class MyBatisResetController {
	
	 @Autowired
	private  UserMapper userMapper;
	
	@RequestMapping("mybatis")  //http://127.0.0.1:8081/springboot_mybatis/mybatis
	public List mybatis() {
 
 		org.apache.ibatis.session.SqlSession x;
		List<User> list= userMapper.selectAll(); 
 		User u= userMapper.selectById(1); 
		return list;
	}
  
}
