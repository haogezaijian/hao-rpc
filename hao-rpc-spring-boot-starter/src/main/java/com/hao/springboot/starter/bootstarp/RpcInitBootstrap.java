package com.hao.springboot.starter.bootstarp;

import com.hao.haorpc.RpcApplication;
import com.hao.haorpc.config.RpcConfig;
import com.hao.haorpc.server.tcp.VertxTcpServer;
import com.hao.springboot.starter.annotation.EnableRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * rpc框架启动
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/10
 */
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {

    /**
     * Spring初始化时执行，初始化RPC框架
     *
     * @param importingClassMetadata importing class metadata
     * @param registry               registry
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/10
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        //获取EnableRpc注解的属性值
        boolean needServer = (boolean)
        importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName()).get("needServer");

        RpcApplication.init();

        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        if (needServer) {
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        } else {
            log.info("不启动 server");
        }
    }
}
