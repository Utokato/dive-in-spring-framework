package cn.llman.config;

import cn.llman.bean.Car;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Bean的生命周期
 * ---  bean的创建 --> bean的初始化 --> bean的销毁
 * <p>
 * Spring 容器管理Bean的生命周期
 * ---  我们可以自定义Bean的初始化和销毁过程
 * ---  容器在bean进行到当前的生命周期时，就会调用我们自定义的初始化和销毁方法
 * <p>
 * 构造(创建对象)
 * ---  1> 单实例：在容器启动时创建对象
 * ---  2> 多实例：在每次获取对象的时候创建
 * <p>
 * 方式：
 * ---  1> 指定初始化和销毁方法
 * ------   通过@Bean注解：指定 init-method(在对象初始化完成后调用) 和 destroy-method(在容器关闭时调用) 方法
 * ------       init-method：在bean初始化完毕后调用(单实例bean在容器创建时就会初始化，多实例bean在获取对象是才会初始化)
 * ------       destroy-method：在容器关闭时调用，单实例的bean随着容器的关闭而销毁；多实例的bean，容器只负责bean的初始化，不负责bean的销毁
 * ---  2> 通过让bean实现{@link InitializingBean} 接口，在afterPropertiesSet方法中定义初始化的逻辑;
 * ---     通过让bean实现{@link DisposableBean} 接口，在destroy方法中定义bean销毁的逻辑。
 * ---  3> 使用 JSR250 规范中的注解：
 * ---      @PostConstruct {@link PostConstruct} 在bean创建完成并属性赋值完成来执行初始化;
 * ---      @PreDestroy {@link PreDestroy} 在容器关闭时，调用bean的方法.
 * ---  4> {@link BeanPostProcessor} : bean的后置处理器，在bean实例化完成，初始化前后进行一些处理工作
 * ---              在此需要明白的是，bean的实例化和初始化是不一样的，实例化时bean的构造，属性赋值等；初始化是指bean已经创建了，再做的一些init工作
 * ---      postProcessBeforeInitialization 在bean初始化(init-method)前进行一些处理工作
 * ---      postProcessAfterInitialization 在bean初始化(init-method)后进行一些处理工作
 * ---
 * ---      源码：{@link AbstractAutowireCapableBeanFactory} 的 initializeBean 方法
 * ---          {applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName); 执行beforeInit方法
 * ---          invokeInitMethods(beanName, wrappedBean, mbd);                      执行init方法
 * ---          applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);  执行afterInit方法}
 * ---          遍历得到容器中所有的BeanPostProcessors：依次循环执行BeforeInitialization，一旦返回null，就跳出循环；
 * ---          上面的三个方法都是在 populateBean(beanName, mbd, instanceWrapper) 方法之后，populateBean是对bean进行属性赋值；
 * <p>
 * ---  Spring底层对{@link BeanPostProcessor}的使用：
 * ---      @Autowired注解，bean生命周期管理，属性的赋值，@Async异步支持 等等，底层都使用了大量的BeanPostProcessor来完成
 * ---
 *
 * @author
 * @date 2018/12/20
 */
@Configuration
@ComponentScan("cn.llman.bean")
public class MainConfigOfLifeCycle {

    @Scope("singleton")
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }

}
