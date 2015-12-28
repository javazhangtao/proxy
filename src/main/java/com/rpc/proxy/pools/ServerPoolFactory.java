package com.rpc.proxy.pools;

import com.rpc.proxy.AbstractProxy;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by zhangtao on 2015/12/15.
 * 池元素工厂
 */
public class ServerPoolFactory extends BaseKeyedPooledObjectFactory<String, Object>{

    private Class<?> targetClazz;

    public ServerPoolFactory(Class<?> targetClazz){
        this.targetClazz=targetClazz;
    }

    @Override
    public Object create(String targetName) throws Exception {
        try {
            if(null==this.targetClazz){
                throw new NullPointerException();
            }
            AbstractProxy proxy=new AbstractProxy();
            proxy.setSourceClazz(this.targetClazz);
            proxy.setServer(true);
            return proxy.getInstance();
        } catch (Exception e) {
           throw(e);
        }
    }

    @Override
    public PooledObject<Object> wrap(Object value) {
        return new DefaultPooledObject<Object>(value);
    }
}
