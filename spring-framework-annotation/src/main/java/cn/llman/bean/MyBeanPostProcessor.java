package cn.llman.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * bean 后置处理器：在bean的init方法前后进行处理
 * 将后置处理器加入到容器中
 *
 * @author
 * @date 2018/12/20
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Some handles before initialization, ... " + beanName + " --> " + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Some handles after initialization, ... " + beanName + " --> " + bean);
        System.out.println(" ");
        return bean;
    }
}
