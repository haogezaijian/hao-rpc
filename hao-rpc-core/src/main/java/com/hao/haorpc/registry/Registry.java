package com.hao.haorpc.registry;

import com.hao.haorpc.config.RegistryConfig;
import com.hao.haorpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/05
 */
public interface Registry {
    /**
     * 初始化
     *
     * @param registryConfig registry config
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/05
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务 (服务端)
     *
     * @param serviceMetaInfo service meta info
     * @throws Exception exception
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/05
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务 (服务端)
     *
     * @param serviceMetaInfo service meta info
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/05
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现 (获取某服务所有节点，消费的)
     *
     * @param serviceKey service key
     * @return {@code List<ServiceMetaInfo> }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/05
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     *
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/05
     */
    void destroy();
}
