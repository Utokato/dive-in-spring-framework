package cn.llman.itest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2018/12/25
 */
@Component
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("Notice! A method before instantiation is running, and the bean prepared to instance is: " + beanName);
        System.out.println();
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (bean instanceof Book) {
            Book book = (Book) bean;
            System.out.println("Fortunately! I am the method after instantiation. I can get a book that is: " + book);
        }
        return false;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Book) {
            Book book = (Book) bean;
            System.out.println("Notice! I am the method before initialization. I can also get a book that is: " + book);
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Book) {
            Book book = (Book) bean;
            System.out.println("Ooh! Fortunately! I am the method after initialization, so I can get a book that is: " + book);
            // try to change the info of this book
            // to imitate a enhance for this bean
            book.setName("Pride and Prejudice");
            book.setPage(699);
        }
        return null;
    }
}
