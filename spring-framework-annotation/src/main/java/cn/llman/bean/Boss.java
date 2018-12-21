package cn.llman.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2018/12/21
 */
@Component
public class Boss {


    private Car car;

    /**
     * 构造器所需要的参数，都是从容器中获取
     * @param car
     */
    // @Autowired
    public Boss(@Autowired Car car) {
        this.car = car;
        System.out.println("Boss constructor with some parameters... ");
    }

    public Car getCar() {
        return car;
    }

    /**
     * 使用 @Autowired 标注在方法(method)上
     * spring容器在创建对象时，就会调用该方法，完成赋值；
     * 方法的参数，自定义类型的值从IOC容器中获取
     *
     * @param car
     */
    // @Autowired
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
