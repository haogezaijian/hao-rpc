package com.hao.haorpc.config;

import lombok.Data;

/**
 * RPC 框架配置
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/01
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "hao-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务端口号
     */
    private Integer serverPort = 8080;
}
