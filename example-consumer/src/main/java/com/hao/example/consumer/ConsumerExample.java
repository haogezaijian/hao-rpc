package com.hao.example.consumer;

import com.hao.haorpc.config.RpcConfig;
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
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
