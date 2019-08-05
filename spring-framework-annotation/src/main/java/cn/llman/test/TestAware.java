package cn.llman.test;

import cn.llman.bean.Daffodil;
import cn.llman.bean.Jasmine;
import cn.llman.config.MainConfigOfAware;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Map;

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

        int count = applicationContext.getBeanDefinitionCount();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        String[] beanNamesForType = applicationContext.getBeanNamesForType(Jasmine.class);
        Map<String, Daffodil> beansOfType = applicationContext.getBeansOfType(Daffodil.class);
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Controller.class);

        System.out.println(jasmine);
        System.out.println(daffodil);

        applicationContext.close();
    }


}
