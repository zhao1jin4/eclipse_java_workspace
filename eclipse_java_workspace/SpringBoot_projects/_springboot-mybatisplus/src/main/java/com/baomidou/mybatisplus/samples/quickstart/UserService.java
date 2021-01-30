package com.baomidou.mybatisplus.samples.quickstart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.samples.quickstart.entity.User;

public interface UserService  extends IService<User> {
    public boolean testIsExistByUserId(); 
}
