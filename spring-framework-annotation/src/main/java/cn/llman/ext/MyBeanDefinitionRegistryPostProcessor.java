package cn.llman.ext;

import cn.llman.itest.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2018/12/26
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    /**
     * BeanDefinitionRegistry Bean定义信息的存储中心
     * 之后，beanFactory就是按照BeanDefinitionRegistry中保存的每一个Bean的定义信息来创建bean实例
     *
     * @param registry
     * @throws BeansException
     */
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("The count of beans in postProcessBeanDefinitionRegistry of MyBeanDefinitionRegistryPostProcessor is: " + registry.getBeanDefinitionCount());
        // RootBeanDefinition beanDefinition = new RootBeanDefinition(Book.class);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Book.class).getBeanDefinition();
        registry.registerBeanDefinition("bookFromRegistry", beanDefinition);
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("The count of beans in postProcessBeanFactory of MyBeanDefinitionRegistryPostProcessor is: " + beanFactory.getBeanDefinitionCount());
    }
}
