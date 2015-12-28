package com.rpc.proxy.pools;

import com.rpc.ServerContants;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * Created by zhangtao on 2015/12/15.
 * 创建服务对象池
 */
public class ServerPoolHandler{
    private ServerPoolConfig poolConfig;//池对象配置信息
    private Class<?> targetClass;
    private ServerPoolHandler(){}

    public ServerPoolHandler(Class<?> targetClass,ServerPoolConfig _poolConfig){
        this.targetClass=targetClass;
        this.poolConfig=_poolConfig;
    }

    /**
     * 创建池对象
     * @throws Exception
     */
    public void create() throws Exception {
        try {
            if(null== ServerContants.serverPool) {
                ServerContants.serverPool = new GenericKeyedObjectPool(new ServerPoolFactory(this.targetClass));
                if (this.poolConfig != null) {
                    GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
                    config.setMaxTotal(this.poolConfig.getMaxTotal());
                    config.setMinIdlePerKey(this.poolConfig.getMaxTotal());
                    config.setMinEvictableIdleTimeMillis(this.poolConfig.getMinEvictableIdleTimeMillis());
                    ServerContants.serverPool.setConfig(config);
                }
            }
        } catch (Exception e) {
            throw(e);
        }
    }
}
