package com.hao.haorpc.config;

import com.hao.haorpc.fault.retry.RetryStrategyKeys;
import com.hao.haorpc.fault.tolerant.TolerantStrategyKeys;
import com.hao.haorpc.loadbalancer.LoadBalancerKeys;
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

    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.NO;

    /**
     * 容错策略
     */
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;

    public Boolean isMock() {
        return this.mock;
    }
}
