package com.hao.haorpc.registry;

import com.hao.haorpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心服务本地缓存
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/06
 */
public class RegistryServiceCache {
    /**
     * 服务缓存
     */
    List<ServiceMetaInfo> serviceCache;

    /**
     * 更新缓存
     *
     * @param newServiceMetaInfo new service meta info
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/06
     */
    void writeCache(List<ServiceMetaInfo> newServiceMetaInfo) {
        this.serviceCache = newServiceMetaInfo;
    }

    /**
     * 读缓存
     *
     * @return {@code List<ServiceMetaInfo> }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/06
     */
    List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     *
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/06
     */
    void clearCache() {
        this.serviceCache = null;
    }
}
