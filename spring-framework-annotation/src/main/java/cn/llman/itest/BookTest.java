package cn.llman.itest;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author
 * @date 2018/12/25
 */
public class BookTest {

    @Test
    public void testOne() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BookConfig.class);
        Book book = applicationContext.getBean(Book.class);
        System.out.println();
        System.out.println("Eureka! I have found a book that detail is: " + book);

        applicationContext.close();
    }
}
