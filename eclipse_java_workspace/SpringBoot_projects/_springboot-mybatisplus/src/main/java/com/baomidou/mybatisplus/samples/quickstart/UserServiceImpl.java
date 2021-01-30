package com.baomidou.mybatisplus.samples.quickstart;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.samples.quickstart.entity.User;
import com.baomidou.mybatisplus.samples.quickstart.mapper.UserMapper;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	public boolean testIsExistByUserId() {
		LambdaQueryWrapper<User> qw = lambdaQuery().getWrapper().eq(User::getAge, 24);
		Integer res = baseMapper.selectCount(qw);
		User user = baseMapper.selectOne(qw);
		return res.compareTo(Integer.valueOf(0)) > 0;
	}

  
}
