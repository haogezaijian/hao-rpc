package com.hao.example.provider;

import com.hao.example.common.service.UserService;
import com.hao.haorpc.registry.LocalRegistry;
import com.hao.haorpc.server.VertxHttpServer;

public class EasyProviderExample {
    public static void main(String[] args) {
        //注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        //启动web服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
