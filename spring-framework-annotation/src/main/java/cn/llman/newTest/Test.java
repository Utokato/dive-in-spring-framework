package cn.llman.newTest;

import cn.llman.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author
 * @date 2019/2/22
 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(MainConfig3.class);

        Father father = applicationContext.getBean(Father.class);
        Child child = applicationContext.getBean(Child.class);

        System.out.println(father.toString());
        System.out.println(child.toString());

        applicationContext.close();
    }
}
