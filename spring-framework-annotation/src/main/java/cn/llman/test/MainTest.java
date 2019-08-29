package cn.llman.test;

import cn.llman.bean.Person;
import cn.llman.config.MainConfig;
import cn.llman.config.MainConfig2;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * @author
 * @date 2018/12/18
 */
public class MainTest {

    @Test
    public void testXMLWay() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);

        printBeans(applicationContext);
    }

    @Test
    public void testAnnotationWay() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);
        printBeans(applicationContext);
    }

    @Test
    public void testAnnotationWay2() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        System.out.println("IOC container has created...");

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.err.println(name);
        }

        BeanDefinition beanDefinition = applicationContext.getBeanDefinition("person");
        System.out.println(beanDefinition);

        Person person1 = applicationContext.getBean("person", Person.class);
        Person person2 = applicationContext.getBean("person", Person.class);
        System.out.println(person1.equals(person2));
    }

    @Test
    public void testAnnotationWay3() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        System.out.println("IOC container has created...");

        Environment environment = applicationContext.getEnvironment();
        String osName = environment.getProperty("os.name");
        System.out.println(osName);


        String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String name : namesForType) {
            System.out.println(name);
        }

        Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
        System.out.println(persons);

    }

    @Test
    public void testAnnotationWayWithImport() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        printBeans(applicationContext);

        Object animalFactoryBean1 = applicationContext.getBean("animalFactoryBean");
        Object animalFactoryBean2 = applicationContext.getBean("animalFactoryBean");
        System.out.println("animalFactoryBean 的类型: " + animalFactoryBean1.getClass());
        System.out.println(animalFactoryBean1.equals(animalFactoryBean2));

        Object animalFactoryBean = applicationContext.getBean("&animalFactoryBean");
        System.out.println("animalFactoryBean 的类型: " + animalFactoryBean.getClass());

    }

    private void printBeans(ApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }


}
