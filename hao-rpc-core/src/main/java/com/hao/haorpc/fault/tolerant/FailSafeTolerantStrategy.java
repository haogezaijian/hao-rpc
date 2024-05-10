package com.hao.haorpc.fault.tolerant;

import com.hao.haorpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 静默处理异常-容错策略
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/10
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
