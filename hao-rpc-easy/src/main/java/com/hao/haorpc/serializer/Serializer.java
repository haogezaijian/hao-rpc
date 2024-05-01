package com.hao.haorpc.serializer;

import java.io.IOException;

/**
 * 序列化器接口
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/01
 */
public interface Serializer {
    /**
     * 序列化
     *
     * @param object object
     * @return {@code byte[] }
     * @throws IOException ioexception
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/01
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 反序列化
     *
     * @param bytes bytes
     * @param type  type
     * @return {@code T }
     * @throws IOException ioexception
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/01
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;
}
