package com.hao.haorpc.serializer;

import java.io.*;

public class JdkSerializer implements Serializer{
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
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        return outputStream.toByteArray();
    }

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
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            objectInputStream.close();
        }
    }
}
