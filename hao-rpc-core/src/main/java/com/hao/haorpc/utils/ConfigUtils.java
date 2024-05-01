package com.hao.haorpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * 配置工具类
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/01
 */
public class ConfigUtils {
    /**
     * 加载配置对象
     *
     * @param tClass t class
     * @param prefix prefix
     * @return {@code T }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/01
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix,"");
    }

    /**
     * 加载配置对象,支持区分环境
     *
     * @param tClass      t class
     * @param prefix      prefix
     * @param environment environment
     * @return {@code T }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/01
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix,String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        return props.toBean(tClass, prefix);
    }
}
