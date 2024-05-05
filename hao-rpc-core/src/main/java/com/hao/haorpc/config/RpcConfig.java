package com.hao.haorpc.config;

import com.hao.haorpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * RPC 框架配置
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/01
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "hao-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private Boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心配置类
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    public Boolean isMock() {
        return this.mock;
    }
}
