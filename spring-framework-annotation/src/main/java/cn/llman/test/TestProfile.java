package cn.llman.test;

import cn.llman.bean.Boss;
import cn.llman.bean.Car;
import cn.llman.bean.Red;
import cn.llman.bean.Worker;
import cn.llman.config.MainConfigAutowire;
import cn.llman.config.MainConfigOfProfile;
import cn.llman.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author
 * @date 2018/12/21
 */
public class TestProfile {

    /**
     * 方式 1> 使用命令行参数的方式(在虚拟机参数位置): -Dspring.profiles.active=test
     * 方式 2> 使用代码的方式激活某种环境
     */
    @Test
    public void testProfile() {
        // 1. 创建applicationContext环境
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 2. 设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("test");
        // 3. 注册配置类
        applicationContext.register(MainConfigOfProfile.class);
        // 4. 启动刷新容器
        applicationContext.refresh();


        printBeans(applicationContext);
        applicationContext.close();
    }

    private void printBeans(ApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }
}
