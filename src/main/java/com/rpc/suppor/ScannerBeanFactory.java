package com.rpc.suppor;

import com.rpc.proxy.AbstractProxy;
import org.springframework.beans.factory.FactoryBean;

/**
 *  Created by zhangtao on 2015/12/29.
 *  创建RPC类对象代理
 */
public class ScannerBeanFactory extends AbstractProxy implements FactoryBean{
    private Class<?> sourceClazz;//原始类对象
    private Object targetObject;//代理目标对象

    @Override
    public Object getObject() throws Exception {
        if(null==this.sourceClazz){
            throw new NullPointerException();
        }
        super.setSourceClazz(this.sourceClazz);
        this.targetObject = super.getInstance();
        return this.targetObject ;
    }

    @Override
    public Class<?> getObjectType() {
        if(null!=this.targetObject){
            return this.targetObject.getClass();
        }
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Class<?> getSourceClazz() {
        return sourceClazz;
    }

    @Override
    public void setSourceClazz(Class<?> sourceClazz) {
        this.sourceClazz = sourceClazz;
    }
}
