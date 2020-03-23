package com.wmx.quartzapp.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * @author wangmaoxiong
 * 1、实现 BeanFactoryAware 接口，重写 setBeanFactory(BeanFactory beanFactory)
 * 2、本类必须是 Spring 组件，所以加上 @Component 注解
 * 3、spring 容器启动实例化本类的时候，会自动执行回调方法 setBeanFactory(BeanFactory beanFactory) 将 BeanFactory 注入
 * 4、获取了 BeanFactory 之后，则可以使用它的任意方法了，比如各种 getBean
 */
@Component
public class BeanFactoryHelper implements BeanFactoryAware {
    private static BeanFactory beanFactory;

    /**
     * 重写 BeanFactoryAware 接口的方法
     *
     * @param beanFactory ：参数赋值给本地属性之后即可使用 BeanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanFactoryHelper.beanFactory = beanFactory;
    }

    /**
     * 根据名称获取容器中的对象实例
     *
     * @param beanName ：注入的实例必须已经存在容器中，否则抛异常：NoSuchBeanDefinitionException
     * @return
     */
    public static Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    /**
     * 根据 class 获取容器中的对象实例
     *
     * @param requiredType ：被注入的必须已经存在容器中，否则抛异常：NoSuchBeanDefinitionException
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return beanFactory.getBean(requiredType);
    }

    /**
     * 判断 spring 容器中是否包含指定名称的对象
     *
     * @param beanName
     * @return
     */
    public static boolean containsBean(String beanName) {
        return beanFactory.containsBean(beanName);
    }
    //其它需求皆可参考 BeanFactory 接口和它的实现类
}