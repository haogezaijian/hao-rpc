package com.hao.haorpc.loadbalancer;

/**
 * 负载均衡器键名常量
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/09
 */
public interface LoadBalancerKeys {

    /**
     * round robin
     */
    String ROUND_ROBIN = "roundRobin";

    /**
     * random
     */
    String RANDOM = "random";

    /**
     * consistent hash
     */
    String CONSISTENT_HASH = "consistentHash";
}
