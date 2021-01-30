package com.baomidou.mybatisplus.samples.quickstart;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.samples.quickstart.entity.User;
import com.baomidou.mybatisplus.samples.quickstart.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {
	//收费版本idea带database插件，可以安装 easy code 插件生成代码
	
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        //List<User> userList = userMapper.selectList(null);//null是查全部
        //Assert.assertEquals(5, userList.size());
        
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("name","age").gt("age",20);
		List<User> userList = userMapper.selectList(queryWrapper); 
        userList.forEach(System.out::println);
        
        
     // LambdaQueryWrapper lambda = new QueryWrapper().lambda();

        LambdaQueryWrapper<User> lambda = new LambdaQueryWrapper<>(); //全部字段
        lambda.like(User::getName,"_J%").lt(User::getAge,40); 
//        List<User> userInfoList = userMapper.selectList(lambda);
//        userInfoList.forEach(System.out::println);
        User  user = userMapper.selectOne(lambda); //可能会为null
        System.out.println(user);
        
    }
    @Test
	public void updateFieldByOther() {

		UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
		updateWrapper.set("name", "lisi");
		// updateWrapper.setSql("age = age - 1");
		updateWrapper.eq("id", 3);
		userMapper.update(null, updateWrapper);

		LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
		lambdaUpdateWrapper.eq(User::getAge, 24).set(User::getName, "abc");
		userMapper.update(null, lambdaUpdateWrapper); 
	}

	
    @Test
    public void testDelete() {
       int effect = userMapper.deleteById(1);//不用写SQL，直接删
       System.out.println(effect);
    }
    @Test
    public void testUpdateDelete() {
        int effect = userMapper.deleteBatchIds(Arrays.asList(2,3));//使用XML中的SQL
        System.out.println(effect);
    }
    @Test
    public void testServiceQuery() {
        userService.testIsExistByUserId();
    }

}
