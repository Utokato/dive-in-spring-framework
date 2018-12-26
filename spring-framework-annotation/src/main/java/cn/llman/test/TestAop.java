package cn.llman.test;

import cn.llman.aop.MathCalculator;
import cn.llman.config.MainConfigOfAOP;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author
 * @date 2018/12/24
 */
public class TestAop {

    @Test
    public void testAopOne() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
        int result = mathCalculator.division(5, 1);
        System.out.println("The result of mathCalculator.division is: " + result);

        applicationContext.close();
    }
}
