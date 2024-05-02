package com.hao.haorpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Mock服务代理(JDK动态代理)
 *
 * @author haoge
 * @version 5.0.0
 * @date 2024/05/02
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     *
     * @param proxy  proxy
     * @param method method
     * @param args   args
     * @return {@code Object }
     * @throws Throwable throwable
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/02
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> methodReturnType = method.getReturnType();
        log.info("mock invoke {}", method.getName());
        return getDefaultObject(methodReturnType);
    }

    /**
     * 生成指定类型的默认值对象
     *
     * @param type type
     * @return {@code Object }
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/02
     */
    private Object getDefaultObject(Class<?> type) {
        //基本类型
        if (type.isPrimitive()) {
            if (type == boolean.class) {
                return false;
            } else if (type == short.class) {
                return (short) 0;
            } else if (type == int.class) {
                return 0;
            } else if (type == long.class) {
                return  0L;
            }
        }
        //对象类型
        return null;
    }


}
