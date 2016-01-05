package com.rpc.suppor.pools;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by zhangtao on 2015/12/15.
 * 服务对象池工具类
 */
public class ServerPoolHandler implements InitializingBean{

    private GenericKeyedObjectPool<String,Object> serverPool=null;//服务池集合
    private ServerPoolConfig serverPoolConfig=new ServerPoolConfig();//代理类池配置

    /**
     * 连接池借用对象
     * @param key
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public  <T> T borrowObject(String key,Class<T> t) throws Exception{
        try {
            return (T) this.serverPool.borrowObject(key);
        } catch (Exception e) {
            throw(e);
        }
    }

    /**
     * 对象归还连接池
     * @param key
     * @param value
     * @throws Exception
     */
    public void returnObject(String key , Object value) throws Exception{
        try {
            this.serverPool.returnObject(key,value);
        } catch (Exception e) {
            throw(e);
        }
    }

    /**
     * 创建池对象
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            if(null== this.serverPool) {
                this.serverPool = new GenericKeyedObjectPool(new ServerPoolFactory());
                if (this.serverPoolConfig != null) {
                    GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
                    config.setMaxTotal(this.serverPoolConfig.getMaxTotal());
                    config.setMinIdlePerKey(this.serverPoolConfig.getMaxTotal());
                    config.setMinEvictableIdleTimeMillis(this.serverPoolConfig.getMinEvictableIdleTimeMillis());
                    this.serverPool.setConfig(config);
                }
            }
        } catch (Exception e) {
            throw(e);
        }
    }

    public ServerPoolConfig getServerPoolConfig() {
        return serverPoolConfig;
    }

    public void setServerPoolConfig(ServerPoolConfig serverPoolConfig) {
        this.serverPoolConfig = serverPoolConfig;
    }

    public GenericKeyedObjectPool<String, Object> getServerPool() {
        return serverPool;
    }

    public void setServerPool(GenericKeyedObjectPool<String, Object> serverPool) {
        this.serverPool = serverPool;
    }
}
