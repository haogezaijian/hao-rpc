package com.hao.haorpc.server.tcp;

import cn.hutool.socket.protocol.Protocol;
import com.hao.haorpc.model.RpcRequest;
import com.hao.haorpc.model.RpcResponse;
import com.hao.haorpc.protocol.ProtocolMessage;
import com.hao.haorpc.protocol.ProtocolMessageDecoder;
import com.hao.haorpc.protocol.ProtocolMessageEncoder;
import com.hao.haorpc.protocol.ProtocolMessageTypeEnum;
import com.hao.haorpc.registry.LocalRegistry;
import io.netty.util.Recycler;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TcpServerHandler implements Handler<NetSocket> {
    @Override
    public void handle(NetSocket netSocket) {


        TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
                //接受请求，解码
                ProtocolMessage<RpcRequest> protocolMessage;
                try {
                    protocolMessage = (ProtocolMessage<RpcRequest>) ProtocolMessageDecoder.decode(buffer);
                } catch (IOException e) {
                    throw new RuntimeException("协议消息解码错误");
                }
                RpcRequest rpcRequest = protocolMessage.getBody();

                //处理请求
                //构造响应结果对象
                RpcResponse rpcResponse = new RpcResponse();
                try {
                    Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                    Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                    Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                    //封装结果返回
                    rpcResponse.setData(result);
                    rpcResponse.setDataType(method.getReturnType());
                    rpcResponse.setMessage("ok");
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    rpcResponse.setMessage(e.getMessage());
                    rpcResponse.setException(e);
                }

                //发送响应，编码
                ProtocolMessage.Header header = protocolMessage.getHeader();
                header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
                ProtocolMessage<RpcResponse> responseProtocolMessage = new ProtocolMessage<>(header, rpcResponse);
                try {
                    Buffer encode = ProtocolMessageEncoder.encode(responseProtocolMessage);
                    netSocket.write(encode);
                } catch (IOException e) {
                    throw new RuntimeException("协议消息编码错误");
                }
        });
        netSocket.handler(bufferHandlerWrapper);
    }
}
