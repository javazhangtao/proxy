package com.rpc.proxy;

import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Created by zhangtao on 2015/12/28.
 * 代理接口
 */
public interface Proxy extends MethodInterceptor {

    /**
     * 获取代理对象实例
     * @return
     * @throws Exception
     */
    public <T> T getInstance(Class<T> sourceClazz) throws Exception;

    /**
     * 获取代理对象类型
     * @return
     * @throws Exception
     */
    public Class<?> getInstanceClass(Class<?> sourceClazz) throws Exception;
}
