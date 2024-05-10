package com.hao.springboot.starter.bootstarp;

import com.hao.haorpc.RpcApplication;
import com.hao.haorpc.config.RegistryConfig;
import com.hao.haorpc.config.RpcConfig;
import com.hao.haorpc.model.ServiceMetaInfo;
import com.hao.haorpc.registry.LocalRegistry;
import com.hao.haorpc.registry.Registry;
import com.hao.haorpc.registry.RegistryFactory;
import com.hao.springboot.starter.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * rpc provider bootstrap
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/10
 */
public class RpcProviderBootstrap implements BeanPostProcessor {

    /**
     * bean初始化后执行，注册服务
     *
     * @param bean     bean
     * @param beanName bean name
     * @return {@code Object }
     * @throws BeansException beans exception
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/10
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        if (rpcService != null) {
            //需要注册服务
            //1.获取服务基本信息
            Class<?> interfaceClass = rpcService.interfaceClass();
            //默认值处理
            if (interfaceClass == void.class) {
                interfaceClass = beanClass.getInterfaces()[0];
            }
            String serviceName = interfaceClass.getName();
            String serviceVersion = rpcService.serviceVersion();

            //2.注册服务
            // 本地注册
            LocalRegistry.register(serviceName, beanClass);

            //全局配置
            final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败", e);
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
