package com.hao.example.provider;

import com.hao.example.common.model.User;
import com.hao.example.common.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名："+user.getName());
        return user;
    }
}
