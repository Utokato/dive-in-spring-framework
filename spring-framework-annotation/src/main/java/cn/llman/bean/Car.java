package cn.llman.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2018/12/20
 */
@Component
public class Car implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Car() {
        System.out.println("Car constructor running... ");
    }

    public void init() {
        System.out.println("Car init...");
    }

    public void destroy() {
        System.out.println("Car destroy...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
