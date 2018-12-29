package cn.llman.config;

import cn.llman.bean.Animal;
import cn.llman.bean.AnimalFactoryBean;
import cn.llman.bean.Color;
import cn.llman.bean.Person;
import cn.llman.condition.LinuxCondition;
import cn.llman.condition.WindowsCondition;
import org.springframework.context.annotation.*;

/**
 * @author
 * @date 2018/12/19
 */
@Configuration
@Conditional({WindowsCondition.class}) // 满足当前条件，这个配置类中的所有bean才会生效; 对类中组件的统一设置
@Import({Color.class,
        Animal.class,
        MyImportSelector.class,
        MyImportBeanDefinitionRegistrar.class
})
// 快速导入组件，id默认是组件的全类名
public class MainConfig2 {

    /**
     * ConfigurableBeanFactory#SCOPE_PROTOTYPE
     * ConfigurableBeanFactory#SCOPE_SINGLETON
     * org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST
     * org.springframework.web.context.WebApplicationContext#SCOPE_SESSION
     * <p>
     * {@link Scope} 使用@Scope调节bean的作用域
     * <p>
     * PROTOTYPE 多实例
     * ---  多实例情况下，在容器启动时不会创建该实例对象;每次需要该实例时，才会调用方法创建该实例;并且，每次获取都会创建不同的实例对象
     * SINGLETON 单实例(默认值)
     * ---  单实例情况下，在容器启动时就会创建该实例对象;以后每次需要实例时，就直接从容器中获取
     * REQUEST 同一次请求创建一个实例
     * SESSION 同一个会话创建一个实例
     * <p>
     * 懒加载：只针对于SINGLETON的bean
     * ---  单实例bean，默认在容器启动时创建bean；
     * ---  使用懒加载，容器在启动时先不创建bean，在第一次获取该bean时，才会去创建，并进行初始化
     */
    @Scope(value = "singleton")
    @Lazy
    @Bean // 默认的组件都是单实例的(SCOPE_SINGLETON)
    public Person person() {
        System.out.println("Inject a bean to container ... ");
        return new Person("malong", 23);
    }

    /**
     * {@link Conditional} 条件注解
     * - @Conditional({Condition})
     * 按照一定的条件进行判断，满足条件给容器注册bean
     */
    @Bean("Bill")
    @Conditional({WindowsCondition.class})
    public Person bill() {
        return new Person("Bill Gates", 23);
    }

    @Bean("Linus")
    @Conditional({LinuxCondition.class})
    public Person linus() {
        return new Person("Linus", 23);
    }


    /**
     * 给容器中注册组件：
     * ---  1. 包扫描 + 组件标注范式(模板)注解 (@Controller,@Service,@Repository,@Component ...) [用于自己的组件上标注]
     * ---  2. @Bean [用于导入第三方包里面的组件]
     * ---  3. @Import [用于快速给容器中导入一个组件] {@link Import}
     * ------   1> @Import(要导入容器中的组件)：容器中就会自动注册这个组件，id默认是组件的全类名
     * ------   2> ImportSelector: 返回要导入的组件的全类名的数组 {@link ImportSelector} {@link MyImportSelector}
     * ------   3> ImportBeanDefinitionRegistrar {@link ImportBeanDefinitionRegistrar} 收到注册bean到容器中
     * ---  4. 使用spring提供的 FactoryBean(工厂bean) 常用于spring与其他框架的整合
     * ------   1> 默认获取到的是工厂bean通过getObject()创建的对象;
     * ------   2> 如果想要获取工厂bean本身，需要在 id 前面加一个&符号：context.getBean("&animalFactoryBean");
     */
    @Bean
    public AnimalFactoryBean animalFactoryBean(){
        return new AnimalFactoryBean();
    }

}
