package com.rpc.suppor;

import com.google.common.base.Strings;
import com.rpc.common.annotations.RC;
import com.rpc.common.annotations.RS;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * Created by zhangtao on 2015/12/29.
 */
public class DefaultBeanNameGenerator extends AnnotationBeanNameGenerator {
    @Override
    protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {
        AnnotationMetadata metadata = annotatedDef.getMetadata();
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(RS.class.getName());
        if(null!=annotationAttributes && !annotationAttributes.isEmpty()) {
            return Strings.emptyToNull(annotationAttributes.get("name") + "");
        }
        Map<String,Object> clientAnnotation=metadata.getAnnotationAttributes(RC.class.getName());
        if(null!=clientAnnotation && !clientAnnotation.isEmpty()){
            return Strings.emptyToNull(clientAnnotation.get("name") + "");
        }
        return super.determineBeanNameFromAnnotation(annotatedDef);
    }
}
