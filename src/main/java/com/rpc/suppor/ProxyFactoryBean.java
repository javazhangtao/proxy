package com.rpc.suppor;

import com.rpc.proxy.AbstractProxy;
import org.springframework.beans.factory.FactoryBean;

/**
 *  Created by zhangtao on 2015/12/29.
 *  创建RPC类对象代理
 */
public class ProxyFactoryBean<T> extends AbstractProxy implements FactoryBean<T>{
    private Class<T> sourceClazz;//原始类对象

    @Override
    public T getObject() throws Exception {
        return getInstance(this.sourceClazz);
    }

    @Override
    public Class<T> getObjectType() {
        return (Class<T>) getInstanceClass(this.sourceClazz);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public Class<T> getSourceClazz() {
        return sourceClazz;
    }

    public void setSourceClazz(Class<T> sourceClazz) {
        this.sourceClazz = sourceClazz;
    }


    //    private T targetObject;//代理目标对象

//    @Override
//    public T getObject() throws Exception {
//        return null;
//    }
//
//    @Override
//    public Class<T> getObjectType() {
//        return (Class<T>) getInstanceClass(this.sourceClazz);
//    }
//
//    @Override
//    public boolean isSingleton() {
//        return false;
//    }



//    public Object getObject() throws Exception {
//        if(null==this.sourceClazz){
//            throw new NullPointerException();
//        }
//        super.setSourceClazz(this.sourceClazz);
//        this.targetObject = super.getInstance();
//        return this.targetObject ;
//    }
//
//    public Class<?> getObjectType() {
//        if(null!=this.targetObject){
//            return this.targetObject.getClass();
//        }
//        return null;
//    }



//    @Override
//    public Class<?> getSourceClazz() {
//        return sourceClazz;
//    }
//
//    @Override
//    public void setSourceClazz(Class<?> sourceClazz) {
//        this.sourceClazz = sourceClazz;
//    }
}
