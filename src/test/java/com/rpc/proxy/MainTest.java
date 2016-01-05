package com.rpc.proxy;

import com.server.UserServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangtao on 2015/12/28.
 */
public class MainTest {

    public static void main(String[] args) {
        ApplicationContext c=new ClassPathXmlApplicationContext("context.xml");
        UserServer u=(UserServer)c.getBean("userServer");
        u.say();
//        try {
//            AbstractProxy bc=new AbstractProxy();
//            bc.setSourceClazz(UserServer.class);
//            ((UserServer)bc.getInstance()).say("123131");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
