package com.hao.haorpc.loadbalancer;

import com.hao.haorpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡器（消费端使用）
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/09
 */
public interface LoadBalancer {
    /**
     * 选着服务调用
     *
     * @param requestParams       request params
     * @param serviceMetaInfoList service meta info list
     * @return {@code ServiceMetaInfo }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/09
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
