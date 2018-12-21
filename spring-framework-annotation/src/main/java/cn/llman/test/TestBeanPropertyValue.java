package cn.llman.test;

import cn.llman.bean.Person;
import cn.llman.config.MainConfigPropertyValue;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author
 * @date 2018/12/21
 */
public class TestBeanPropertyValue {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigPropertyValue.class);

    @Test
    public void testPropertyValue() {
        System.out.println("Container has created.");
        printBeans(applicationContext);

        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String nickName = environment.getProperty("person.nickName");
        System.out.println(nickName);

        applicationContext.close();
    }


    private void printBeans(ApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }
}
