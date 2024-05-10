package com.hao.haorpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.hao.haorpc.RpcApplication;
import com.hao.haorpc.config.RpcConfig;
import com.hao.haorpc.constant.RpcConstant;
import com.hao.haorpc.fault.retry.RetryStrategy;
import com.hao.haorpc.fault.retry.RetryStrategyFactory;
import com.hao.haorpc.fault.tolerant.TolerantStrategy;
import com.hao.haorpc.fault.tolerant.TolerantStrategyFactory;
import com.hao.haorpc.loadbalancer.LoadBalancer;
import com.hao.haorpc.loadbalancer.LoadBalancerFactory;
import com.hao.haorpc.model.RpcRequest;
import com.hao.haorpc.model.RpcResponse;
import com.hao.haorpc.model.ServiceMetaInfo;
import com.hao.haorpc.protocol.*;
import com.hao.haorpc.registry.Registry;
import com.hao.haorpc.registry.RegistryFactory;
import com.hao.haorpc.serializer.JdkSerializer;
import com.hao.haorpc.serializer.Serializer;
import com.hao.haorpc.serializer.SerializerFactory;
import com.hao.haorpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 服务代理(JDK动态代理)
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/01
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //指定序列化器
        String serviceName = method.getDeclaringClass().getName();
        //构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
            //从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            //负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            //发送TCP请求
            //重试机制
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectServiceMetaInfo)
            );
        } catch (Exception e) {
            //容错机制
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null,e);
        }

            return rpcResponse.getData();

    }
}
