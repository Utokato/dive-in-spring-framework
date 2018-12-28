package cn.llman.ext;

import cn.llman.bean.Animal;
import cn.llman.itest.Book;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListenerMethodProcessor;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.*;

import java.util.List;

/**
 * 扩展原理：
 * <p>
 * BeanPostProcessor {@link BeanPostProcessor} Bean后置处理器，bean创建对象初始化前后进行拦截工作
 * 一、BeanFactoryPostProcessor {@link BeanFactoryPostProcessor}
 * -    BeanFactory后置处理器，在beanFactory的标准初始化之后调用：所有的bean定义已经保存加载到工厂中，但是工厂还没有创建任何的bean实例
 * -    原理：
 * -    1> 首先通过构造函数构造一个IOC容器，在构造函数中会调用refresh()方法
 * -    2> 在{@link AbstractApplicationContext#refresh()}中调用invokeBeanFactoryPostProcessors(beanFactory)方法执行BeanFactoryPostProcessors
 * -        如何找到所有的BeanFactoryPostProcessor并执行它们的方法：
 * -            1) 直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并根据优先级顺序执行它们的方法
 * -            2) 在初始化创建其他组件之前执行
 * <p>
 * 二、BeanDefinitionRegistryPostProcessor {@link BeanDefinitionRegistryPostProcessor} 是 BeanFactoryPostProcessor 的一个子接口
 * -    postProcessBeanDefinitionRegistry();
 * -    所有的bean定义信息将要被加载，bean实例还没有创建
 * -    优先于BeanFactoryPostProcessor执行
 * -    可以利用BeanDefinitionRegistryPostProcessor给容器中额外添加一些组件
 * -    原理：
 * -    1> 首先通过构造函数构造一个IOC容器，在构造函数中会调用refresh()方法
 * -    2> 在refresh()方法中，调用invokeBeanFactoryPostProcessors(beanFactory)方法
 * -        {@link PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory, List)}
 * -    3> 从容器中获取所有BeanDefinitionRegistryPostProcessor类型的组件：
 * -        a. 先从容器中获取BeanDefinitionRegistryPostProcessor类型的组件
 * -           并对这些组件按照优先级进行排序，然后依次触发postProcessBeanDefinitionRegistry方法
 * -        b. 然后会执行这些BeanDefinitionRegistryPostProcessor类型的组件中定义的postProcessBeanFactory方法
 * -    4> 然后再从容器中获取所有BeanFactoryPostProcessor类型的组件：
 * -        a. 也就是上面所说的了，对所有BeanFactoryPostProcessor类型的组件按照优先级进行排序
 * -        b. 然后触发它们的postProcessBeanFactory方法
 * -    5> 上面的流程，都在初始化创建其他组件之前执行
 * <p>
 * 三、ApplicationListener: 监听容器中发布的事件，完成事件驱动模型的开发
 * -    {@link ApplicationListener#onApplicationEvent(ApplicationEvent)}
 * -    监听ApplicationEvent及其子事件
 * -    基于事件驱动的开发步骤：
 * -        1. 写一个监听(该监听器必须实现ApplicationListener)器来监听某个事件(该事件必须实现ApplicationEvent接口或其子接口)
 * -            或者使用@EventListener注解, 详情参看{@link UserService}
 * -                原理：使用{@link EventListenerMethodProcessor}处理器来解析方法上的注解(@EventListener)
 * -                这个处理器实现了一个接口{@link SmartInitializingSingleton}
 * -                SmartInitializingSingleton的工作原理见下文
 * -        2. 把监听器加入到容器中
 * -        3. 只要容器中有相关事件的发布，我们就能监听到该事件
 * -            如：ContextRefreshedEvent 容器刷新事件(所有的bean都完成创建)会发布这个事件
 * -                ContextClosedEvent 容器关闭会发布这个事件
 * -        4. 发布自定义事件：applicationContext.publishEvent()
 * -    事件驱动的原理：
 * -        ContextRefreshedEvent、TestExt$1[source=I have published a event]、ContextClosedEvent
 * -        1. ContextRefreshedEvent事件：
 * -            1> 首先通过构造函数构造一个IOC容器，在构造函数中会调用refresh()方法
 * -            2> 在refresh方法中调用finishRefresh(), 表示容器刷新完成,
 * -            3> 在finishRefresh方法中，调用了publishEvent(new ContextRefreshedEvent(this))，会发布ContextRefreshedEvent事件
 * -            4> 事件发布的流程如下
 * -        2. 然后发布自定义事件TestExt$1[source=I have published a event]，同样走下面的事件发布流程
 * -        3. 最后在容器关闭时，发布ContextClosedEvent事件，同样走下面的事件发布流程
 * -        [ 事件发布的流程 ]：
 * -            a. 首先获取到事件的多播器(派发器)：getApplicationEventMulticaster()
 * -            b. 调用multicastEvent进行事件派发：
 * -                1) getApplicationListeners 获取所有的ApplicationListener，然后进行for循环：
 * -                2) 在for循环中，如果能够获取Executor，可以支持使用Executor进行异步派发；
 * -                3) 如果获取不到Executor，就同步的方式直接执行listener方法：invokeListener(listener, event)
 * -                4) 在invokeListener中执行doInvokeListener方法
 * -                5) 在doInvokeListener方法中，拿到每一个listener，然后使用listener回调onApplicationEvent方法
 * -        [ 事件的多播器(派发器)是如何获取的? ]
 * -            a. 首先通过构造函数构造一个IOC容器，在构造函数中会调用refresh()方法
 * -            b. 在refresh方法中，会调用initApplicationEventMulticaster()方法，初始化应用事件多播器
 * -                1) 先判断容器中是否包含一个id为applicationEventMulticaster的bean
 * -                2) 如果没有，就this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory)对象
 * -                3) 并通过beanFactory.registerSingleton()方法将该bean加入到容器中
 * -                4) 之后，就可以在其他组件需要派发事件时，自动从容器中获取该applicationEventMulticaster
 * -        [ 多播器(派发器)是如何获取到事件监听器的? getApplicationListeners? ]
 * -            a. 首先通过构造函数构造一个IOC容器，在构造函数中会调用refresh()方法
 * -            b. 在refresh方法中，会调用registerListeners()方法
 * -            c. 在registerListeners方法中，首先获取到所有的listenerBeanNames
 * -                然后在for循环中，getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName)
 * -                将listenerBeanName加入到多播器中
 * <p>
 * - {@link SmartInitializingSingleton} 的工作原理：
 * -    1. 首先通过构造函数构造一个IOC容器，在构造函数中会调用refresh()方法
 * -    2. 在refresh方法中，会调用finishBeanFactoryInitialization()，初始化剩下的单实例bean
 * -    3. 在finishBeanFactoryInitialization方法中，会调用preInstantiateSingletons()方法
 * -    4. 在preInstantiateSingletons方法中：
 * -        1> 先通过getBean()创建所有的单实例bean
 * -        2> 获取所有创建好的单实例Bean，判断是否是SmartInitializingSingleton类型的bean
 * -            如果是，就调用smartSingleton.afterSingletonsInstantiated()
 *
 * @author
 * @date 2018/12/26
 */
@Configuration
@ComponentScan({"cn.llman.ext"})
public class ExtConfig {

    @Bean
    public Animal animal() {
        return new Animal();
    }

}
