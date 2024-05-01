package com.hao.haorpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalRegistry {
    /**
     * 注册信息存储
     */
    private static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 注册服务
     *
     * @param serviceName service name
     * @param implClass   impl class
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/01
     */
    public static void register(String serviceName, Class<?> implClass) {
        map.put(serviceName, implClass);
    }

    /**
     * 获取服务
     *
     * @param serviceName service name
     * @return {@code Class<?> }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/01
     */
    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    /**
     * 删除服务
     *
     * @param serviceName service name
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/01
     */
    public static void remove(String serviceName) {
        map.remove(serviceName);
    }
}
