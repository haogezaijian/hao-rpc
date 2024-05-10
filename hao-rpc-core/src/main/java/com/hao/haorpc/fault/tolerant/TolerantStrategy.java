package com.hao.haorpc.fault.tolerant;

import com.hao.haorpc.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/10
 */
public interface TolerantStrategy {
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
