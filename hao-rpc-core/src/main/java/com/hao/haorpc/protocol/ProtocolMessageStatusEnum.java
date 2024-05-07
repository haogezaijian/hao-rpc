package com.hao.haorpc.protocol;

import lombok.Getter;

/**
 * 协议消息的状态枚举
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/07
 */
@Getter
public enum ProtocolMessageStatusEnum {
    OK("ok",20),
    BAD_REQUEST("badRequest",40),
    BAD_RESPONSE("badResponse", 50);

    private final  String text;

    private final int value;

    ProtocolMessageStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 Value获取枚举
     *
     * @param value value
     * @return {@code ProtocolMessageStatusEnum }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/07
     */
    public static ProtocolMessageStatusEnum getEnumByValue(int value) {
        for (ProtocolMessageStatusEnum anEnum : ProtocolMessageStatusEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }
}
