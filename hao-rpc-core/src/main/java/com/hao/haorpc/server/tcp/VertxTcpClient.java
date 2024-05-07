package com.hao.haorpc.server.tcp;

import cn.hutool.core.util.IdUtil;
import com.hao.haorpc.RpcApplication;
import com.hao.haorpc.model.RpcRequest;
import com.hao.haorpc.model.RpcResponse;
import com.hao.haorpc.model.ServiceMetaInfo;
import com.hao.haorpc.protocol.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * vertx tcp 请求客户端
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/07
 */
public class VertxTcpClient {

    /**
     * 发送请求
     *
     * @param rpcRequest      rpc request
     * @param serviceMetaInfo service meta info
     * @return {@code RpcResponse }
     * @throws ExecutionException   execution exception
     * @throws InterruptedException interrupted exception
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/07
     */
    public static RpcResponse doRequest(RpcRequest rpcRequest, ServiceMetaInfo serviceMetaInfo) throws ExecutionException, InterruptedException {
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        netClient.connect(serviceMetaInfo.getServicePort(), serviceMetaInfo.getServiceHost(), result -> {
            if (!result.succeeded()) {
                System.out.println("Failed to connect to TCP server");
                return;
            }

            System.out.println("Connected to TCP server");
            NetSocket netSocket = result.result();
            //发送数据
            //构造消息
            ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
            ProtocolMessage.Header header = new ProtocolMessage.Header();
            header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
            header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
            header.setSerializer((byte) ProtocolMessageSerializerEnum.getEnumByValue(RpcApplication.getRpcConfig().getSerializer()).getKey());
            header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
            header.setRequestId(IdUtil.getSnowflakeNextId());
            protocolMessage.setHeader(header);
            protocolMessage.setBody(rpcRequest);
            //编码
            try {
                Buffer encode = ProtocolMessageEncoder.encode(protocolMessage);
                netSocket.write(encode);
            } catch (IOException e) {
                throw new RuntimeException("协议消息编码错误");
            }

            //接收响应
            TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
                try {
                    ProtocolMessage<RpcResponse> rpcResponseProtocolMessage =
                            (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                    responseFuture.complete(rpcResponseProtocolMessage.getBody());
                } catch (IOException e) {
                    throw new RuntimeException("协议消息解码错误");
                }
            });
            netSocket.handler(bufferHandlerWrapper);
        });

        RpcResponse rpcResponse = responseFuture.get();
        netClient.close();
        return rpcResponse;
    }
}
