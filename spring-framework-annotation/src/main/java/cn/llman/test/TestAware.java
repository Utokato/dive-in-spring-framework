package cn.llman.test;

import cn.llman.bean.Daffodil;
import cn.llman.bean.Jasmine;
import cn.llman.config.MainConfigOfAware;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author
 * @date 2018/12/29
 */
public class TestAware {

    @Test
    public void testAwareOne() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAware.class);

        Jasmine jasmine = applicationContext.getBean(Jasmine.class);
        Daffodil daffodil = applicationContext.getBean(Daffodil.class);

        System.out.println(jasmine);
        System.out.println(daffodil);

        applicationContext.close();
    }
}
