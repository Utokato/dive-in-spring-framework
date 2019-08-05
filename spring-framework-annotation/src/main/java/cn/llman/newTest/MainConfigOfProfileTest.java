package cn.llman.newTest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author
 * @date 2019/2/26
 */
public class MainConfigOfProfileTest {
    /**
     * 方式 1> 使用命令行参数的方式(在虚拟机参数位置): -Dspring.profiles.active=test
     * 方式 2> 使用代码的方式激活某种环境
     */
    public static void main(String[] args) {
        // 1. 创建applicationContext环境
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 2. 设置需要激活的环境
        context.getEnvironment().setActiveProfiles("test");
        // 3. 注册配置类
        context.register(MainConfigOfProfile.class);
        // 4. 启动刷新容器
        context.refresh();

        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        context.close();
    }
}
