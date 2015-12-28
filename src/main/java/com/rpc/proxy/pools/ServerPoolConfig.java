package com.rpc.proxy.pools;

/**
 * Created by zhangtao on 2015/12/9.
 * 代理类池配置
 */
public class ServerPoolConfig {

    final Integer DEFAULT_MAXTOTAL=5000;
    final Long DEFAULT_TIMEMILLIS=60*60*1000l;
    final Integer DEFAULT_MINIDLEPREKEY=50;

    private Integer maxTotal=DEFAULT_MAXTOTAL;//池最大对象
    private Integer minidlePrekey=DEFAULT_MINIDLEPREKEY;//单个key对应最小池元素
    private Long minEvictableIdleTimeMillis=DEFAULT_TIMEMILLIS;//对象空闲最小等待时间

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxIdle) {
        this.maxTotal = maxIdle;
    }

    public Integer getMinidlePrekey() {
        return minidlePrekey;
    }

    public void setMinidlePrekey(Integer minidlePrekey) {
        this.minidlePrekey = minidlePrekey;
    }

    public Long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }
}
