package com.rpc.proxy;

import com.rpc.common.annotations.RS;
import com.rpc.proxy.utils.ByteCode;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by zhangtao on 2015/12/28.
 * 方法动态代理
 */
public class AbstractProxy implements Proxy {
    Logger logger=Logger.getLogger(AbstractProxy.class);

    @Override
    public Class<?> getInstanceClass(Class<?> sourceClazz) {
        try {
            if(null == sourceClazz){
                throw new NullPointerException();
            }
            return sourceClazz;
        } catch (NullPointerException e) {
            logger.error(e);
        }
        return null ;
    }

    @Override
    public <T> T getInstance(Class<T> sourceClazz) throws Exception{
        getInstanceClass(sourceClazz);
        if(sourceClazz.isInterface()){
            ByteCode byteCode=new ByteCode();
            byteCode.setSourceInterFace(sourceClazz);
            sourceClazz=byteCode.createClass();
        }
        Object targetObject=sourceClazz.newInstance();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(sourceClazz);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        boolean isServer = false;
        try {
            isServer = ifServer(obj.getClass());
        }catch (Exception e){
            throw(e);
        }
        if(isServer){
            System.out.println(method.getName()+"----server");
            //返回RpcResponse对象   给netty框架回调
        }else{
            System.out.println(method.getName()+"----client");
            //封装RpcRequest对象    给netty传输框架，请求服务端  并接收服务端回调
        }
        return null;
    }


    /**
     * 判断被扫描类是否服务
     * @return
     * @throws Exception
     */
    private boolean ifServer(Class<?> sourceClazz) throws Exception{
        Annotation[] annotationClazzs=sourceClazz.getAnnotations();
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
}
