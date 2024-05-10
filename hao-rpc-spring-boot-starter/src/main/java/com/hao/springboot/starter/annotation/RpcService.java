package com.hao.springboot.starter.annotation;


import com.hao.haorpc.constant.RpcConstant;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务提供者注解
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/10
 */
@Target ({ElementType.TYPE})
@Retention (RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

    /**
     * 服务接口
     *
     * @return {@code Class<?> }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/10
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     *
     * @return {@code String }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/10
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;
}
