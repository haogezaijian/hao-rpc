package com.hao.haorpc.fault.retry;

import com.hao.haorpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 重试策略
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/09
 */
public interface RetryStrategy {
    /**
     * 重试
     *
     * @param callable callable
     * @return {@code RpcResponse }
     * @throws Exception exception
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/09
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception ;
}
