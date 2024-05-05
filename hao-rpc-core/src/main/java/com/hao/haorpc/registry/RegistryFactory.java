package com.hao.haorpc.registry;

import com.hao.haorpc.spi.SpiLoader;

/**
 * 注册中心工厂
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/05
 */
public class RegistryFactory {
    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     *  获取实例
     *
     * @param key key
     * @return {@code Registry }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/05
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }
}
