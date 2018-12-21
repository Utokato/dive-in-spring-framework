package cn.llman.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author
 * @date 2018/12/19
 */
public class LinuxCondition implements Condition {

    /**
     * ConditionContext 判断条件能否使用的上下文环境
     * AnnotatedTypeMetadata 注释信息
     *
     * @param context
     * @param metadata
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        // 获取IOC容器使用的 BeanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        // 获取类加载器 ClassLoader
        ClassLoader classLoader = context.getClassLoader();

        // 获取当前运行的环境，JVM OS 等
        Environment environment = context.getEnvironment();
        // 获取bean定义的注册类
        BeanDefinitionRegistry registry = context.getRegistry();

        // 判断容器中bean的注册情况，也可以给容器中注册bean
        boolean existPerson = registry.containsBeanDefinition("person");
        if (existPerson) {
            return true;
        }

        String osName = environment.getProperty("os.name");
        if (osName.contains("Linux")) {
            // 判断是否为Linux系统
            return true;
        }

        return false;
    }
}
