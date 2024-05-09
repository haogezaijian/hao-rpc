package com.hao.haorpc.loadbalancer;

import com.hao.haorpc.spi.SpiLoader;

/**
 * 负载均衡器工厂
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/09
 */
public class LoadBalancerFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }

    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}
