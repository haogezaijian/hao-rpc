package com.hao.haorpc.fault.tolerant;

import com.hao.haorpc.model.RpcResponse;

import java.util.Map;

/**
 * 快速失败容错策略
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/10
 */
public class FailFastTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
