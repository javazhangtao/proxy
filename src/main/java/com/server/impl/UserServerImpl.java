package com.server.impl;

import com.rpc.common.annotations.RS;
import com.server.UserServer;

/**
 * Created by zhangtao on 2015/12/29.
 */
@RS(name="userServer")
public class UserServerImpl implements UserServer {
    @Override
    public void say() {
        System.out.println("null");
    }

    @Override
    public void say(String aaa) {
        System.out.println(aaa);
    }
}
