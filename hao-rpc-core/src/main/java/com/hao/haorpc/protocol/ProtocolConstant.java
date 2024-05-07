package com.hao.haorpc.protocol;

/**
 * 协议常量
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/07
 */
public interface ProtocolConstant {
    /**
     * 消息头长度
     */
    int MESSAGE_HEADER_LENGTH = 17;

    /**
     * 协议魔数
     */
    byte PROTOCOL_MAGIC = 0x1;

    /**
     * 协议版本号
     */
    byte PROTOCOL_VERSION = 0x1;
}
