package junit.mybatis;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import mybatis.MybatisSpringBoot;
import mybatis.dao.UserMapper;
import mybatis.vo.User;

//测试 OK
@RunWith(SpringRunner.class)
@SpringBootTest(classes=MybatisSpringBoot.class) //本类的所在包名无所谓
@SpringBootConfiguration
@ContextConfiguration
public class SpringBootMyBatisTest {

	@Autowired
	private  UserMapper userMapper;
	
	@Test
	public void testMyBatis() {
		List<User> list= userMapper.selectAll();
		System.out.println(list);
	}
 
}
