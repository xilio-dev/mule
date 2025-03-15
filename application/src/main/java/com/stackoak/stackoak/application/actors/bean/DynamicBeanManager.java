package com.stackoak.stackoak.application.actors.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.util.function.Function;

/**
 * 动态bean实例注册管理器
 */
@Service
public class DynamicBeanManager {
    @Autowired
    private ConfigurableApplicationContext context;

    /**
     * 动态注册销毁bean属性
     *
     * @param clazz    bean类型
     * @param function 配置信息
     * @param <T>      类型
     */
    public <T> void register(Class<T> clazz, Function<BeanDefinitionBuilder, BeanDefinitionBuilder> function) {
        String className = ClassUtils.getShortName(clazz);
        String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getBeanFactory();
        // 销毁bean的实例信息
        if (factory.containsBean(beanName)) {
            factory.destroySingleton(beanName);
        }
        //删除bean的定义信息
        if (factory.containsBeanDefinition(beanName)) {
            factory.removeBeanDefinition(beanName);
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        BeanDefinitionBuilder apply = function.apply(builder);
        factory.registerBeanDefinition(beanName, apply.getBeanDefinition());
    }
}
