package com.rpc.proxy;

import com.rpc.common.annotations.RS;
import com.rpc.proxy.utils.ByteCode;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by zhangtao on 2015/12/28.
 * 方法动态代理
 */
public class AbstractProxy implements Proxy , MethodInterceptor{
    private Class<?> sourceClazz;//需要代理原始class

    @Override
    public Object getInstance() throws Exception{
        if(null==sourceClazz){
            throw new NullPointerException();
        }
        if(sourceClazz.isInterface()){
            ByteCode byteCode=new ByteCode();
            byteCode.setSourceInterFace(this.sourceClazz);
            this.sourceClazz=byteCode.createClass();
        }
        Object targetObject=this.sourceClazz.newInstance();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.sourceClazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        boolean isServer = false;
        try {
            isServer = ifServer();
        }catch (Exception e){
            throw(e);
        }
        if(isServer){
            //返回RpcResponse对象   给netty框架回调
        }else{
            //封装RpcRequest对象    给netty传输框架，请求服务端  并接收服务端回调
        }
        return null;
    }


    /**
     * 判断被扫描类是否服务
     * @return
     * @throws Exception
     */
    private boolean ifServer() throws Exception{
        Annotation[] annotationClazzs=this.sourceClazz.getAnnotations();
        if(null!=annotationClazzs && annotationClazzs.length>0){
            for (Annotation annotation:annotationClazzs){
                if(annotation.getClass().getName().equals(RS.class.getName())){
                    return true ;
                }else{
                    return false ;
                }
            }
            return false ;
        }else{
            throw new NullPointerException("sourceClazz's  annotation is null ");
        }
    }

    public Class<?> getSourceClazz() {
        return sourceClazz;
    }

    public void setSourceClazz(Class<?> sourceClazz) {
        this.sourceClazz = sourceClazz;
    }
}
