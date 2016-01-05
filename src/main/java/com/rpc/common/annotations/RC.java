package com.rpc.common.annotations;

import java.lang.annotation.*;

/**
 * Created by zhangtao on 2015/12/29.
 * RPC客户端注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RC {
    String name();
}
