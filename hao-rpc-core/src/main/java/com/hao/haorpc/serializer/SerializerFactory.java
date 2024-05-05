package com.hao.haorpc.serializer;

import com.hao.haorpc.spi.SpiLoader;

/**
 * 序列化器工厂 （用于获取序列化器对象）
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/02
 */
public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     *
     * @param key key
     * @return {@code Serializer }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/02
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
