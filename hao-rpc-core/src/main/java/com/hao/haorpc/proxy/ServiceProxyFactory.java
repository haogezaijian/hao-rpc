package com.hao.haorpc.proxy;

import com.hao.haorpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂 (用于创建代理对象)
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/01
 */
public class ServiceProxyFactory {
    /**
     * 根据服务类获取代理对象
     *
     * @param serviceClass service class
     * @return {@code T }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/01
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        if (RpcApplication.getRpcConfig().isMock()) {
            return getMockPRoxy(serviceClass);
        }
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy()
        );
    }

    /**
     * 根据服务获取Mock代理对象
     *
     * @param serviceClass service class
     * @return {@code T }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/02
     */
    private static <T> T getMockPRoxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy()
        );
    }
}
