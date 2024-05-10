package com.hao.springboot.starter.annotation;

import com.hao.haorpc.constant.RpcConstant;
import com.hao.haorpc.fault.retry.RetryStrategyKeys;
import com.hao.haorpc.fault.tolerant.TolerantStrategyKeys;
import com.hao.haorpc.loadbalancer.LoadBalancer;
import com.hao.haorpc.loadbalancer.LoadBalancerKeys;

/**
 * 服务消费者注解
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/10
 */
public @interface RpcReference {
    Class<?> interfaceClass() default void.class;

    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    String loadBalancer() default LoadBalancerKeys.ROUND_ROBIN;

    String retryStrategy() default RetryStrategyKeys.NO;

    String tolerantStrategy() default TolerantStrategyKeys.FAIL_FAST;

    boolean mock() default false;
}
