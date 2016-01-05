package com.rpc.suppor.pools;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by zhangtao on 2015/12/15.
 * 池元素工厂
 */
public class ServerPoolFactory extends BaseKeyedPooledObjectFactory<String, Object> implements ApplicationContextAware{
    private ApplicationContext context = null;

    @Override
    public Object create(String targetName) throws Exception {
        try {
            Object targetObject = this.context.getBean(targetName);
            if(null!=targetObject) {
                return targetObject;
            }else{
                throw new NullPointerException("serverpool: not found bean with name ["+targetName+"]");
            }
        } catch (Exception e) {
           throw(e);
        }
    }

    @Override
    public PooledObject<Object> wrap(Object value) {
        return new DefaultPooledObject<Object>(value);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }
}
