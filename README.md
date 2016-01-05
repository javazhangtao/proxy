# proxy spring+netty+common-pools2+CGLIB  集成简单RPC框架
服务端配置：
<bean class="com.rpc.suppor.ScannerConfigure">
    <property name="basePackage" value="com.server"/>#需要暴露服务所在的包路径，支持通配符，必须
    <property name="annotationClass" value="com.rpc.common.annotations.RS"/>#需要扫描的注解，必须
    <property name="ifServerPool" value="true"/>#服务端是否使用池对象优化，选填。缺省 false
    <property name="serverPoolConfig">
        <bean class="com.rpc.suppor.pools.ServerPoolConfig"></bean>
    </property>#服务端使用池对象优化后，池对象配置信息，选填。缺省
</bean>
客户端配置：
<bean class="com.rpc.suppor.ScannerConfigure">
    <property name="basePackage" value="com.server"/>#需要调用远程服务所在的包路径，支持通配符，必须
    <property name="annotationClass" value="com.rpc.common.annotations.RC"/>#需要扫描的注解，必须
</bean>
传输配置：
