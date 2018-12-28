package cn.llman.ext;

import cn.llman.itest.Book;
import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author
 * @date 2018/12/26
 */
public class TestExt {

    @Test
    public void testOne() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);


        // 发布事件
        applicationContext.publishEvent(new ApplicationEvent("I have published a event") {
        });

        Book bean = applicationContext.getBean(Book.class);

        applicationContext.close();

    }
}
