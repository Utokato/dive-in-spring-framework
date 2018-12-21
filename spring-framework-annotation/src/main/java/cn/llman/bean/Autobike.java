package cn.llman.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2018/12/20
 */
@Component
public class Autobike implements InitializingBean, DisposableBean {

    public Autobike() {
        System.out.println("Autobike is constructing...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Autobike destroy...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Autobike init...");
    }
}
