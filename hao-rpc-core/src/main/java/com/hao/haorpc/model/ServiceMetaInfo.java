package com.hao.haorpc.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 服务元信息 （注册信息）
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/05
 */
@Data
public class ServiceMetaInfo {
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务版本号
     */
    private String serviceVersion = "1.0";

    /**
     * 服务地址
     */
    private String serviceAddress;

    /**
     * 服务IP
     */
    private String serviceHost;

    /**
     * 服务端口
     */
    private Integer servicePort;

    /**
     * 服务分组 （未实现）
     */
    private String serviceGroup = "default";

    /**
     * 获取服务键名
     *
     * @return {@code String }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/05
     */
    public String getServiceKey() {
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /**
     * 获取服务注册节点键名
     *
     * @return {@code String }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/05
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s", getServiceKey(), serviceHost);
    }

    /**
     * 获取完整服务地址
     *
     * @return {@code String }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/05
     */
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%d", serviceHost, servicePort);
        }
        return String.format("%s:%d", serviceHost, servicePort);
    }
}
