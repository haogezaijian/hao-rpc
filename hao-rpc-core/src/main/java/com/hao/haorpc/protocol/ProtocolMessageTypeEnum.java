package com.hao.haorpc.protocol;

import lombok.Getter;

/**
 * 协议消息类型枚举
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/07
 */
@Getter
public enum ProtocolMessageTypeEnum {
    REQUEST(0),
    RESPONSE(1),
    HEATR_BEAT(2),
    OTHERS(3);

    private final int key;

    ProtocolMessageTypeEnum(int key) {
        this.key = key;
    }

    /**
     * 根据 key 获取枚举
     *
     * @param key key
     * @return {@code ProtocolMessageTypeEnum }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/07
     */
    public static ProtocolMessageTypeEnum getEnumByKey(int key) {
        for (ProtocolMessageTypeEnum anEnum : ProtocolMessageTypeEnum.values()) {
            if (anEnum.key == key) {
                return anEnum;
            }
        }
        return null;
    }
}
