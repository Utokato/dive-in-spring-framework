package cn.llman.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author
 * @date 2018/12/20
 */
@Component
public class Bicycle {

    public Bicycle() {
        System.out.println("Bicycle is constructing...");
    }

    @PostConstruct
    public void init() {
        System.out.println("Bicycle init running, after Bicycle construction...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Bicycle method running, before Bicycle destroy...");
    }

}
