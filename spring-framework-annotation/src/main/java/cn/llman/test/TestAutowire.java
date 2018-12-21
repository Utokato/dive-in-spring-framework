package cn.llman.test;

import cn.llman.bean.Boss;
import cn.llman.bean.Car;
import cn.llman.bean.Red;
import cn.llman.bean.Worker;
import cn.llman.config.MainConfigAutowire;
import cn.llman.dao.BookDao;
import cn.llman.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author
 * @date 2018/12/21
 */
public class TestAutowire {

    @Test
    public void testAutowireOne() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigAutowire.class);
        System.out.println("IOC Container has created.");
        // printBeans(applicationContext);
        BookService bookService = applicationContext.getBean(BookService.class);
        bookService.print();

        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);
        Car car = applicationContext.getBean(Car.class);
        System.out.println("The car of boss is equal the car in IOC container: " + boss.getCar().equals(car));

        Worker worker = applicationContext.getBean(Worker.class);
        System.out.println("The car of boss is equal the car of worker: " + boss.getCar().equals(worker.getCar()));

        Red red = applicationContext.getBean(Red.class);
        System.out.println("ApplicationContext from Red class is equal this context: " + red.getApplicationContext().equals(applicationContext));

        applicationContext.close();
    }

    private void printBeans(ApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }
}
