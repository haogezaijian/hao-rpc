package com.hao.example.consumer;

import com.hao.example.common.model.User;
import com.hao.example.common.service.UserService;
import com.hao.haorpc.config.RpcConfig;
import com.hao.haorpc.proxy.ServiceProxyFactory;
import com.hao.haorpc.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/01
 */
public class ConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("haoge");
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        short number = userService.getNumber();
        System.out.println("number = " + number);
    }
}
