package cn.llman.test;

import cn.llman.bean.Car;
import cn.llman.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author
 * @date 2018/12/20
 */
public class TestBeanLifeCycle {

    @Test
    public void testWithAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        System.out.println("Container has created.");
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
