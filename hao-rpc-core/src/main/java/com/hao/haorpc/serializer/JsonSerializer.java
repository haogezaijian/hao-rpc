package com.hao.haorpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hao.haorpc.model.RpcRequest;
import com.hao.haorpc.model.RpcResponse;

import java.io.IOException;

/**
 * json 序列化器
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/02
 */
public class JsonSerializer implements Serializer {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <T> byte[] serialize(T object) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        T obj = OBJECT_MAPPER.readValue(bytes, type);
        if (obj instanceof RpcRequest) {
            return handleRequest((RpcRequest) obj, type);
        }
        if (obj instanceof RpcResponse) {
            return handleResponse((RpcResponse) obj, type);
        }
        return obj;
    }

    /**
     * handle response
     *
     * @param rpcResponse rpc response
     * @param type        type
     * @return {@code T }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/02
     */
    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        //处理响应数据
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType()));
        return type.cast(rpcResponse);
    }

    /**
     * handle request
     *
     * @param rpcRequest rpc request
     * @param type       type
     * @return {@code T }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/02
     */
    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();

        //循环处理每个参数的类型
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> clazz = parameterTypes[i];
            //如果类型不同，则重新处理一下类型
            if (!clazz.isAssignableFrom(args[i].getClass())) {
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(argBytes, clazz);
            }
        }
        return type.cast(rpcRequest);
    }
}
