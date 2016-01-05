package com.rpc.suppor;

import com.rpc.suppor.pools.ServerPoolConfig;
import com.rpc.suppor.pools.ServerPoolHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

/**
 * Created by zhangtao on 2015/12/29.
 * RPC服务扫描配置
 */
public class ScannerConfigure implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {
    Logger logger=Logger.getLogger(ScannerConfigure.class);
    private String beanName;
    private ApplicationContext context;
    private String basePackage;//扫描路径，支持通配符
    private BeanNameGenerator beanNameGenerator=new DefaultBeanNameGenerator();//spring   bean命名策略
    private String annotationClass ;//需要扫描的注解
    private boolean ifServerPool=false;//是否启用服务端对象池优化
    private ServerPoolConfig serverPoolConfig;//服务池对象配置信息

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //连接池对象添加入spring容器管理并初始化
        if(this.ifServerPool){
            String serverPoolBeanName="serverPoolHandler";
            if(!registry.containsBeanDefinition(serverPoolBeanName)){
                BeanDefinitionBuilder poolDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ServerPoolHandler.class);
                if(null!=this.serverPoolConfig){
                    poolDefinitionBuilder.addPropertyValue("serverPoolConfig",this.serverPoolConfig);
                }
                registry.registerBeanDefinition(serverPoolBeanName,poolDefinitionBuilder.getRawBeanDefinition());
            }
        }
        processPropertyPlaceHolders();
        Scanner scanner=new Scanner(registry);
        if(StringUtils.hasText(this.annotationClass)){
            Class annotationClazz = null ;
            try {
                annotationClazz=Class.forName(this.annotationClass);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
            if(annotationClazz.isAnnotation()){
                scanner.setAnnotationClass(annotationClazz.asSubclass(Annotation.class));
            }else{
                throw new IllegalStateException("property [annotationClass] is not a  annotation ");
            }
        }else{
            throw new NullPointerException("property [annotationClass] is null ");
        }
        scanner.setResourceLoader(this.context);
        scanner.setContext(this.context);
        scanner.registerFilters();
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}


    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage, "Property 'basePackage' is required");
    }

    private void processPropertyPlaceHolders(){
        Map<String, PropertyResourceConfigurer> prcs = this.context.getBeansOfType(PropertyResourceConfigurer.class);
        if (!prcs.isEmpty() && this.context instanceof GenericApplicationContext) {
            BeanDefinition scannerBean = ((GenericApplicationContext) this.context).getBeanFactory().getBeanDefinition(this.beanName);
            DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
            factory.registerBeanDefinition(this.beanName, scannerBean);
            for (PropertyResourceConfigurer prc : prcs.values()) {
                prc.postProcessBeanFactory(factory);
            }
        }
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public BeanNameGenerator getBeanNameGenerator() {
        return beanNameGenerator;
    }

    public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
        this.beanNameGenerator = beanNameGenerator;
    }

    public String getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(String annotationClass) {
        this.annotationClass = annotationClass;
    }

    public boolean isIfServerPool() {
        return ifServerPool;
    }

    public void setIfServerPool(boolean ifServerPool) {
        this.ifServerPool = ifServerPool;
    }
}
