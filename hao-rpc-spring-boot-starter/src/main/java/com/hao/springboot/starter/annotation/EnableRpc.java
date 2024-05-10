package com.hao.springboot.starter.annotation;

import com.hao.springboot.starter.bootstarp.RpcConsumerBootstrap;
import com.hao.springboot.starter.bootstarp.RpcInitBootstrap;
import com.hao.springboot.starter.bootstarp.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动Rpc注解
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/10
 */
@Target ({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 需要启动server
     *
     * @return boolean
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/10
     */
    boolean needServer() default true;
}
