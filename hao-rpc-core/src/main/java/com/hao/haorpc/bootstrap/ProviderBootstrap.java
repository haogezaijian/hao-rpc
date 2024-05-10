package com.hao.haorpc.bootstrap;

import com.hao.haorpc.RpcApplication;
import com.hao.haorpc.config.RegistryConfig;
import com.hao.haorpc.config.RpcConfig;
import com.hao.haorpc.model.ServiceMetaInfo;
import com.hao.haorpc.model.ServiceRegisterInfo;
import com.hao.haorpc.registry.LocalRegistry;
import com.hao.haorpc.registry.Registry;
import com.hao.haorpc.registry.RegistryFactory;
import com.hao.haorpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者初始化
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/10
 */
public class ProviderBootstrap {
    /**
     * init
     *
     * @param serviceRegisterInfoList service register info list
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/10
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        //RPC框架初始化
        RpcApplication.init();
        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            //本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            //注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败", e);
            }
        }

        //启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
