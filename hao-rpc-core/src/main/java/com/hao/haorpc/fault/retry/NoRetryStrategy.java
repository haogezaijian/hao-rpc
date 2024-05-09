package com.hao.haorpc.fault.retry;

import com.hao.haorpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 不重试策略
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/09
 */
public class NoRetryStrategy implements RetryStrategy{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
