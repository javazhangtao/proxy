package com.rpc.suppor;

import com.rpc.common.annotations.RS;
import com.rpc.suppor.pools.ServerPoolHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by zhangtao on 2015/12/29.
 * RPC服务主扫描程序
 */
public class Scanner extends ClassPathBeanDefinitionScanner{
    Logger logger=Logger.getLogger(Scanner.class);
    private BeanNameGenerator beanNameGenerator=new DefaultBeanNameGenerator();//spring   bean命名策略
    private Class<? extends Annotation> annotationClass ;//需要扫描的注解
    public Scanner(BeanDefinitionRegistry registry) {
        super(registry);
    }
    private ApplicationContext context;

    /**
     * 添加扫描默认过滤器
     */
    public void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
    }

    /**
     * spring 扫描主程序
     * @param basePackages
     * @return
     */
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        super.setBeanNameGenerator(this.beanNameGenerator);
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if(beanDefinitions.isEmpty()){
            logger.warn("not have rpc's bean found");
        }else{
            try {
                for (BeanDefinitionHolder holder:beanDefinitions){
                    GenericBeanDefinition definition=(GenericBeanDefinition)holder.getBeanDefinition();//获取spring实例对象bean
                    definition.setScope("prototype");//设置spring管理bean为多例
                    definition.getPropertyValues().add("sourceClazz", definition.getBeanClassName());
                    definition.setBeanClass(ScannerBeanFactory.class);
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return (beanDefinition.getMetadata().isInterface() || beanDefinition.getMetadata().isIndependent());
    }

    public BeanNameGenerator getBeanNameGenerator() {
        return beanNameGenerator;
    }

    @Override
    public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
        this.beanNameGenerator = beanNameGenerator;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     *  服务端类存入Pool连接池
     */
    private void addServer2Pool(String key, ApplicationContext context) throws Exception{
        ServerPoolHandler poolHandler=null;
        try {
            poolHandler=(ServerPoolHandler)context.getBean("serverPoolHandler");
        } catch (BeansException e) {}
        if(null!=poolHandler){
            if(StringUtils.hasText(key)){
                Object targetObject = null;
                try {
                    targetObject = context.getBean(key);
                } catch (BeansException e) {}
                RS rs=targetObject.getClass().getAnnotation(RS.class);
                if(null!=rs){
                    return ;
                }else{
                        poolHandler.getServerPool().addObject(key);
                        logger.info("[server started :] "+key+" into pool");

                }
            }else{
                throw new NullPointerException("server2pool:targetObject's key is null ");
            }
        }
    }
}
