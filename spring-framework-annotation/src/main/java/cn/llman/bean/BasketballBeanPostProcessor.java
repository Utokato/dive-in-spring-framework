package cn.llman.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author lma
 * @date 2019/08/08
 */
@Component
public class BasketballBeanPostProcessor implements BeanPostProcessor, InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanClass.equals(Basketball.class)) {
            System.err.println("BasketballBeanPostProcessor.BeforeInstantiation -> invoked!");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(Basketball.class)) {
            System.err.println("BasketballBeanPostProcessor.AfterInstantiation -> invoked!");
        }
        return false;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(Basketball.class)) {
            System.err.println("BasketballBeanPostProcessor.BeforeInitialization -> invoked!");
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(Basketball.class)) {
            System.err.println("BasketballBeanPostProcessor.AfterInitialization -> invoked!");
        }
        return null;
    }
}
