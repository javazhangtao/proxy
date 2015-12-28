package com.rpc;

import com.rpc.proxy.pools.ServerPoolConfig;
import net.sf.cglib.reflect.FastClass;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;

/**
 * Created by zhangtao on 2015/12/28.
 * 服务静态资源
 */
public class ServerContants {

    public static GenericKeyedObjectPool<String,FastClass> serverPool=null;//服务池集合
    public static ServerPoolConfig poolConfig=new ServerPoolConfig();//代理类池配置
}
