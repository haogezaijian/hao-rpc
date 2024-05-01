package com.hao.haorpc.proxy;

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
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy()
        );
    }
}
