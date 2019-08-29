package cn.llman.test;

import cn.llman.config.MainConfigWithBeanPostProcessor;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author
 * @date 2018/12/25
 */
public class TestWithBeanPostProcessor {

    @Test
    public void testWithBeanPostProcessor() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigWithBeanPostProcessor.class);
        printBeans(applicationContext);
        applicationContext.close();

    }

    private void printBeans(ApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }

}
