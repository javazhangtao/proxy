package com.rpc.proxy;

import com.rpc.proxy.utils.ByteCode;

/**
 * Created by zhangtao on 2015/12/28.
 */
public class MainTest {

    public static void main(String[] args) {
        try {
            ByteCode bc=new ByteCode();
            bc.setSourceInterFace(UserServer.class);
            ((UserServer)bc.getObject()).say();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
