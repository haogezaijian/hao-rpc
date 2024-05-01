package com.hao.example.provider;

import com.hao.example.common.service.UserService;
import com.hao.haorpc.RpcApplication;
import com.hao.haorpc.registry.LocalRegistry;
import com.hao.haorpc.server.VertxHttpServer;

/**
 * 简易服务消费者示例
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/01
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        //RPC框架初始化
        RpcApplication.init();

        //注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        //启动web服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
