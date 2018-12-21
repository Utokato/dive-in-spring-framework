package cn.llman.config;

import cn.llman.bean.Car;
import cn.llman.bean.Worker;
import cn.llman.dao.BookDao;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * 自动装配：
 * ---  Spring利用依赖注入(DI),完成对IOC容器中各个组件依赖关系的赋值
 * <p>
 * -
 * 1. @Autowired {@link Autowired} 自动注入
 * ---  1> 默认优先按照类型去容器中找相应的组件，找到就去赋值
 * ---  2> 如果找到多个相同类型的组件，再将属性的名称作为id去容器中寻找
 * ---  3> 使用@Qualifier {@link Qualifier} 来指定需要装配的组件的id，而不是使用属性名
 * ---  4> 默认，一定要将所有的属性赋好值，如果发现某个组件在容器中没有(No such bean)，就会报错
 * ------   默认情况下，@Autowired的required = true意思是必须装配，required = false情况下，装配就不是必须的
 * ---  5> @Primary {@link Primary} 让spring在自动装配时，默认使用首选的bean，即被@Primary标注的bean
 * ------   @Primary 和 @Qualifier 的优先级问题：当这两个注解同时存在时，@Qualifier最终起作用；
 * ------   如：@Primary修饰bean(bookDao2)，此时bean(bookDao2)是默认首选的bean，但是在@Autowired处使用@Qualifier("bookDao");
 * ------       最终自动装配的是bookDao，而不是bookDao2
 * <p>
 * 2. Spring还支持使用 @Resource(JSR250){@link Resource}和 @Inject(JSR330) {@link Inject}, 这两个注解都是Java规范
 * ---  @Resource 可以和 @Autowired 一样实现自动装配功能；
 * ---  @Resource 默认是按照组件的名称进行装配, 也可以通过@Resource(name = "bookDao2")去指定注入的bean
 * ---  但是@Resource不支持@Primary，也不支持@Autowired(required = false)的方式
 * ---  @Inject: 需要导入javax.Inject包，和@Autowired的功能一样支持@Primary，但是@Inject不支持@Autowired(required = false)的方式
 * <p>
 * ---  AutowiredAnnotationBeanPostProcessor {@link AutowiredAnnotationBeanPostProcessor} ：解析完成自动装配的功能
 * <p>
 * 3. @Autowired：构造器，参数，方法，属性; 无论标注在什么位置，都是用IOC容器中获取参数组件
 * ---  1> 标注在方法位置, @Bean+方法参数，参数从容器中获取；默认可以省略@Autowired
 * ---  2> 标注在构造器位置，如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，所需要的参数依然可以从组件中正确获取
 * ---  3> 标注在参数位置
 *
 * 4. 自定义组件想要使用Spring容器底层的一些组件(如：ApplicationContext,BeanFactory,xxx...)
 * ---  自定义组件只需要实现 xxxAware 接口 {@link Aware}: 在创建对象时，会调用接口规定的方法注入相关组件
 * ---  xxxAware的功能是使用 xxxAwareProcessor来完成的：
 * ---      ApplicationContextAware <--> ApplicationContextAwareProcessor
 *
 *
 * @author
 * @date 2018/12/21
 */
@Configuration
@ComponentScan({"cn.llman.dao", "cn.llman.service", "cn.llman.controller", "cn.llman.bean"})
public class MainConfigAutowire {

    @Primary
    @Bean("bookDao2")
    public BookDao bookDao() {
        BookDao bookDao = new BookDao();
        bookDao.setLabel("Manner value");
        return bookDao;
    }

    /**
     * 使用@Bean标注向容器中注入bean时，方法的参数从容器中获取
     * 参数上的@Autowired 可以省略
     *
     * @param car
     * @return
     */
    @Bean
    public Worker worker(@Autowired Car car) {
        Worker worker = new Worker();
        worker.setCar(car);
        return worker;
    }

}
