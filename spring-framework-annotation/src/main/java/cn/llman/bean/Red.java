package cn.llman.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @author
 * @date 2018/12/21
 */
@Component
public class Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("--> IOC injected is: " + applicationContext);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("--> Current bean name is: " + name);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String string = resolver.resolveStringValue("hello, ${os.name}, I am #{18*20}.");
        System.out.println("--> A string resolved is: " + string);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
