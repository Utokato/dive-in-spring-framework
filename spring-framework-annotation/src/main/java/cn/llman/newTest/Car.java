package cn.llman.newTest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author
 * @date 2019/2/23
 */
public class Car {

    public Car() {
        System.out.println("Car constructor running... ");
    }

    public void init() {
        System.out.println("Car init...");
    }

    public void destroy() {
        System.out.println("Car destroy...");
    }

}
