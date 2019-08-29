package cn.llman.test;

import cn.llman.bean.Basketball;
import cn.llman.bean.Football;
import cn.llman.config.MainConfigNew;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author lma
 * @date 2019/08/08
 */
public class MainTestNew {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigNew.class);

        Football football = context.getBean(Football.class);
        Basketball basketball = context.getBean("basketball", Basketball.class);

        System.err.println(football);
        System.err.println(basketball);

        System.out.println("=========");

        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

        System.out.println("=========");

        context.close();

    }
}
