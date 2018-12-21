package cn.llman.config;

import cn.llman.bean.Pig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author
 * @date 2018/12/19
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * AnnotationMetadata
     * BeanDefinitionRegistry: bean 定义的注册类
     * ---  把所有需要注册到容器中的bean，调用BeanDefinitionRegistry的registerBeanDefinition来手动注册
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean existDog = registry.containsBeanDefinition("cn.llman.bean.Dog");
        boolean existCat = registry.containsBeanDefinition("cn.llman.bean.Cat");
        if (existCat && existDog) {
            // 指定bean的信息(bean的类型等..)
            RootBeanDefinition beanDefinition = new RootBeanDefinition(Pig.class);
            System.out.println("Preparing to inject a bean named Pig ...");
            // 注册一个bean，并指定bean的名称
            registry.registerBeanDefinition("Pig", beanDefinition);
        }
    }
}
