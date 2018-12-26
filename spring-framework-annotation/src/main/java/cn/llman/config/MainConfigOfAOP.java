package cn.llman.config;

import cn.llman.aop.LogAspects;
import cn.llman.aop.MathCalculator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.*;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.*;

import java.lang.reflect.Method;

/**
 * AOP：(动态代理)
 * -    在程序运行期间动态地将某段代码切入到指定方法的指定位置进行运行的编程方式
 * <p>
 * 1> AOP 依赖：Spring AOP(spring-aspects)
 * 2> 定义一个业务逻辑类 {@link MathCalculator#division(int, int)}
 * -    在业务逻辑运行的时候进行日志打印(运行前，运行后，出现异常时...)
 * 3> 定义一个日志切面类 {@link LogAspects}
 * -    切面类里面的方法需要动态感知MathCalculator#division运行的当前位置，并执行相应的方法
 * -    即：通知方法：包括：
 * -                    前置通知(@Before) {@link LogAspects#logStart(JoinPoint)}      在目标方法运行之前运行
 * -                    后置通知(@After) {@link LogAspects#logEnd(JoinPoint)}        在目标方法结束之后运行
 * -                    返回通知(@AfterReturning) {@link LogAspects#logReturn(JoinPoint, Object)}     在目标方法正常返回之后运行
 * -                    异常通知(@AfterThrowing) {@link LogAspects#logException(JoinPoint, Throwable)}  在目标方法发生异常之后运行
 * -                    环绕通知(@Around) 动态代理，手动推进目标方法中运行(joinPoint.processed() )
 * 4> 给切面类的目标方法，标注何时何地运行(通知注解)
 * 5> 将切面类和业务逻辑类(目标方法所在类)都加入到容器中
 * 6> 必须告诉spring容器哪个是切面类(给切面类加上一个注解 {@link Aspect})
 * 7> 在配置类中启用启动代理 {@link EnableAspectJAutoProxy} (开启基于注解方式的aop模式)
 * -        在Spring中有很多的 @EnableXxxx ; 可以用来替代以前的xml格式来开启某些功能
 * -        如：@EnableAspectJAutoProxy用来开启切面的自动代理
 * <p>
 * 完成AOP需要如下步骤：
 * 1. 将业务逻辑类{@link MathCalculator}和切面类{@link LogAspects}都加到容器中，并声明{@link Aspect}(告诉)Spring哪个是切面类
 * 2. 在切面类的每一个通知方法上标注通知注解，告诉Spring何时何地运行(写好切点表达式)
 * 3. 在配置类(或xml配置文件中)开启基于注解的AOP模式{@link EnableAspectJAutoProxy}
 * <p>
 * AOP 原理 [ 看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么? ]
 * -    @EnableAspectJAutoProxy：
 * -        1. @EnableAspectJAutoProxy是什么?
 * -            利用@Import(AspectJAutoProxyRegistrar.class)，给容器中导入AspectJAutoProxyRegistrar组件
 * -                再利用{@link AspectJAutoProxyRegistrar} 给容器中导入bean：(BeanDefinition)
 * -                internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator
 * -                给容器中注册一个 AnnotationAwareAspectJAutoProxyCreator
 * -
 * -        2. {@link AnnotationAwareAspectJAutoProxyCreator} 的功能是什么?
 * -                先看继承关系：(详情参看uml类图)
 * -                    AnnotationAwareAspectJAutoProxyCreator
 * -                        -> {@link AspectJAwareAdvisorAutoProxyCreator}
 * -                            -> {@link AbstractAdvisorAutoProxyCreator}
 * -                                -> {@link AbstractAutoProxyCreator}
 * -                                    implements {@link SmartInstantiationAwareBeanPostProcessor}, {@link BeanFactoryAware}
 * -                    关注后置处理器(BeanPostProcessor 在bean初始化完成前后做些事情)、自动注入BeanFactory.
 * -
 * -           AbstractAutoProxyCreator#setBeanFactory(BeanFactory)
 * -           AbstractAutoProxyCreator#有后置处理器的逻辑
 * -
 * -           AbstractAdvisorAutoProxyCreator#setBeanFactory(BeanFactory)#initBeanFactory()
 * -
 * -           // AspectJAwareAdvisorAutoProxyCreator中没有与后置处理器和BeanFactory相关的信息
 * -
 * -           AnnotationAwareAspectJAutoProxyCreator#initBeanFactory() 覆盖了父类的initBeanFactory()方法
 * -
 * -        流程：
 * -            1> 传入主配置类，创建IOC容器
 * -            2> 注册配置类，通过refresh()刷新容器
 * -            3> 在{@link AbstractApplicationContext#refresh()} 中调用了 {@link PostProcessorRegistrationDelegate#registerBeanPostProcessors(ConfigurableListableBeanFactory, AbstractApplicationContext)}
 * -                1) 先获取IOC容器中已经定义了的需要创建对象的所有BeanPostProcessor
 * -                2) 给容器中添加一些额外需要的BeanPostProcessor
 * -                3) 优先注册实现了PriorityOrdered接口的BeanPostProcessor
 * -                4) 再给容器中注册实现了Ordered接口的BeanPostProcessor
 * -                5) 最后注册没有实现任何优先级接口的BeanPostProcessor
 * -                6) 注册BeanPostProcessor，本质上就是创建BeanPostProcessor对象，保存在容器中
 * -                    {@link AbstractAutowireCapableBeanFactory}
 * -                    创建名为internalAutoProxyCreator的BeanPostProcessor，类型为(AnnotationAwareAspectJAutoProxyCreator):
 * -                        a. 创建bean的实例
 * -                        b. populateBean: 给bean的属性赋值
 * -                        c. initializeBean: 初始化bean;
 * -                            1> {@link AbstractAutowireCapableBeanFactory#invokeAwareMethods(String, Object)}: 处理Aware接口方法的回调
 * -                            2> applyBeanPostProcessorsBeforeInitialization: 在初始化前执行BeanPostProcessor#postProcessBeforeInitialization
 * -                            3> {@link AbstractAutowireCapableBeanFactory#invokeInitMethods(String, Object, RootBeanDefinition)}: 执行初始化方法
 * -                            4> applyBeanPostProcessorsAfterInitialization: 在初始化后执行BeanPostProcessor#postProcessAfterInitialization
 * -                        d. BeanPostProcessor(*AnnotationAwareAspectJAutoProxyCreator*)创建成功：--> aspectJAdvisorsBuilder
 * -                7) 把BeanPostProcessor注册到BeanFactory中：beanFactory.addBeanPostProcessor(beanPostProcessor);
 * -            ----- 以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程 -----
 * -                AnnotationAwareAspectJAutoProxyCreator -> InstantiationAwareBeanPostProcessor
 * -                注意： postProcessBeforeInstantiation  postProcessAfterInstantiation
 * -                      postProcessBeforeInitialization   postProcessAfterInitialization
 * -            4> {@link AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)} 完成BeanFactory的实例化工作：创建剩下的bean
 * -                1) 遍历获取容器中所有的Bean,依次创建对象getBean(beanName)
 * -                    getBean() -> doGetBean() -> getSingleton()
 * -                2) 创建Bean:
 * -                   【AnnotationAwareAspectJAutoProxyCreator会在所有bean创建之前有一个拦截，因为它是InstantiationAwareBeanPostProcessor的子类，
 * -                    所以会调用postProcessBeforeInstantiation()方法】
 * -                    [ BeanPostProcessor 是在bean对象创建完成，初始化前后调用的]
 * -                    [ InstantiationAwareBeanPostProcessor 是在创建bean实例之前先尝试用后置处理器返回对象 ]
 * -                    a. 先从缓存中获取当前的bean，如果能获取到，说明bean是之前被创建了，直接使用，否则再去创建
 * -                        只要被创建的bean都会被缓存起来，Spring正是利用了这点做到了所有的实例的Singleton模式
 * -                    b. createBean() 创建bean；AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前尝试返回bean的实例
 * -                        1> resolveBeforeInstantiation(beanName, mbdToUse) 解析BeforeInstantiation
 * -                            希望后置处理器能够返回一个代理对象；如果能返回代理对象就直接使用，如果不能就继续执行
 * -                                1) 后置处理器先尝试返回对象
 * -                                {@link AbstractAutowireCapableBeanFactory#resolveBeforeInstantiation(String, RootBeanDefinition)}
 * -                                {@link AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInstantiation(Class, String)}
 * -                                bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 * -                                拿到所有的后置处理器，如果是InstantiationAwareBeanPostProcessor，就执行postProcessorsBeforeInstantiation
 * -					            if (bean != null) {
 * -						            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 * -                                }
 * -                        2> {@link AbstractAutowireCapableBeanFactory#doCreateBean(String, RootBeanDefinition, Object[])},真正的去创建一个bean实例，和上面3.6里面的流程是一致的
 * -
 * - {@link AnnotationAwareAspectJAutoProxyCreator} 是一个 {@link InstantiationAwareBeanPostProcessor} 类型的后置处理器，它的作用：
 * - 1> 每一个bean创建之前，调用{@link AnnotationAwareAspectJAutoProxyCreator#postProcessBeforeInstantiation(Class, String)}
 * -       关心MathCalculator和LogAspects的创建：
 * -       1) 判断当前的bean是否在 advisedBeans 中(advisedBeans保存了所有需要增强的bean; 所谓需要增强的bean，就是需要被aop增强的bean);
 * -       2) 判断当前的bean是否是基础类型，即是否实现了Advice、Pointcut、Advisor、AopInfrastructureBean，或者是否是切面@Aspect
 * -       3) 判断是否需要跳过：
 * -           a. 获取候选的增强器(增强器就是切面里的通知方法)，将这些增强器封装在一个集合中[ List<Advisor> candidateAdvisors ]
 * -               每一个封装的通知方法的增强器是 InstantiationModelAwarePointcutAdvisor ;
 * -               在shouldSkip中判断每一个增强器是否为AspectJPointcutAdvisor类型，答案是否定的
 * -           b. 上面的判断，在spring4.x的时候永远返回false，在5.1后，需要通过一个{@link AutoProxyUtils#isOriginalInstance(String, Class)} 方法去判断
 * - 2> 经过postProcessBeforeInstantiation后，开始真正的执行bean的创建创建，就构造函数的执行，即bean的实例化
 * - 3> 完成了bean的实例化后，继续执行{@link AnnotationAwareAspectJAutoProxyCreator#postProcessAfterInstantiation(Object, String)}
 * -    这个方法直接返回了true
 * - 4> 实例化，及其实例化的后置通知方法执行完毕后，开始执行实例化的后置通知方法，首先是{@link AnnotationAwareAspectJAutoProxyCreator#postProcessBeforeInitialization(Object, String)};
 * -    这个方法直接返回了该实例bean
 * - 5> 接着执行{@link AnnotationAwareAspectJAutoProxyCreator#postProcessAfterInitialization(Object, String)};
 * -    这个方法中执行了 wrapIfNecessary(bean, beanName, cacheKey) {@link AnnotationAwareAspectJAutoProxyCreator#wrapIfNecessary(Object, String, Object)}
 * -    wrapIfNecessary意思就是如果在需要的情况下，对bean进行包装，如何进行包装呢?
 * -        1) 获取当前bean的所有增强方法(通知方法) Object[] specificInterceptors {@link AnnotationAwareAspectJAutoProxyCreator#getAdvicesAndAdvisorsForBean(Class, String, TargetSource)}
 * -            a. 找到候选的所有的增强器(找哪些通知方法是需要切入到当前bean的方法中)
 * -            b. 获取当前bean能够使用的增强器，封装到一个集合中 List<Advisor> eligibleAdvisors
 * -            c. 对这个eligibleAdvisors增强器集合进行排序，eligibleAdvisors = sortAdvisors(eligibleAdvisors)
 * -        2) 保存当前bean在advisedBeans中
 * -        3) 如果当前bean需要增强，创建当前bean的代理对象，{@link AnnotationAwareAspectJAutoProxyCreator#createProxy(Class, String, Object[], TargetSource)}
 * -            a. 获取所有的增强器(通知方法)，这里的拦截器，即通知方法
 * -            b. 保存到代理工厂中(proxyFactory)
 * -            c. 创建代理对象 {@link DefaultAopProxyFactory#createAopProxy(AdvisedSupport)}
 * -                JdkDynamicAopProxy(config)      JDK动态代理
 * -                ObjenesisCglibAopProxy(config)  CGlib动态代理
 * -        4) wrapIfNecessary最终的结果就是给容器中返回一个当前组件经过cglib增强的代理对象
 * -        5) 以后容器中获取到的就是这个组件的代理对象，执行目标方法时，代理对象就会执行通知方法的流程
 * -
 * - 6> 目标方法的执行流程：
 * -    IOC 容器中保存了组件的代理对象(cglib增强后的对象)，这个代理对象中保存了详细信息(如：增强器，目标对象，xxx)
 * -    1) 首先来到了 {@link CglibAopProxy #DynamicAdvisedInterceptor#intercept} 类的 intercept 方法，用于拦截目标方法的执行
 * -    2) 根据ProxyFactory获取将要执行的目标方法的拦截器链(chain):
 * -        List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 * -        {@link DefaultAdvisorChainFactory#getInterceptorsAndDynamicInterceptionAdvice(Advised, Method, Class)}
 * -
 * -        a. List<Object> interceptorList = new ArrayList<>(advisors.length); 保存所有的拦截器(增强器，即通知方法)
 * -            本次调用中advisors.length为5，包含了1个ExposeInvocationInterceptor 和 4个增强器；
 * -        b. 遍历所有的增强器(advisors)，将其转换为一个一个的interceptor(registry.getInterceptors(advisor)) {@link DefaultAdvisorAdapterRegistry#getInterceptors(Advisor)}
 * -        c. 将增强器转为MethodInterceptor并加入List<MethodInterceptor>中
 * -            如果是MethodInterceptor类型，直接加入到集合中
 * -            如果不是MethodInterceptor类型，通过适配器模式，使用AdvisorAdapter(增强器的适配器)将增强器转为MethodInterceptor，然后加入集合中
 * -                这里的适配器有三种：MethodBeforeAdviceAdapter, AfterReturningAdviceAdapter, ThrowsAdviceAdapter
 * -                分别对相应了 方法执行前通知方法的适配器，方法执行后通知方法的适配器，方法执行出现异常通知方法的适配器
 * -            转换完成后，返回MethodInterceptor数组(interceptors.toArray(new MethodInterceptor[0]))
 * -        d. 拦截器(每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制)
 * -
 * -    3) 如果没有拦截器链，直接执行目标方法
 * -    4) 如果有拦截器链，把需要执行的目标对象，目标方法，拦截器链等所有信息传入创建一个CglibMethodInvocation对象，并调用proceed()方法
 * -       Object retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
 * -    5) CglibMethodInvocation对象调用proceed()方法的过程，其实就是拦截器链的触发过程
 * -        a. 如果没有拦截器，直接执行目标方法；或者拦截器的索引和拦截器数组的长度-1相等，即执行最后一个拦截时，执行目标方法
 * -        b. 链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行；
 * -            通过拦截器链的机制，保证了通知方法与目标方法的执行顺序。
 * <p>
 * - 总结：
 * -    1> 通过 @EnableAspectJAutoProxy 开启AOP功能
 * -    2> @EnableAspectJAutoProxy 会通过 @Import 注解向容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator
 * -    3> AnnotationAwareAspectJAutoProxyCreator 这个组件本质上是一个 InstantiationAwareBeanPostProcessor 类型的后置处理器
 * -    4> InstantiationAwareBeanPostProcessor 在这种类型的处理器和它的父类中，总共定义了4个方法，对应了bean实例化的2个阶段(实例化和初始化)
 * -        {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation(Class, String)}
 * -        {@link InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation(Object, String)}
 * -        {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInitialization(Object, String)}
 * -        {@link InstantiationAwareBeanPostProcessor#postProcessAfterInitialization(Object, String)}
 * -    5> 容器的创建流程：
 * -        1) 在AnnotationConfigApplicationContext的构造函数中会调用 {@link AbstractApplicationContext#refresh()}方法
 * -        2) 在refresh()方法中，会调用registerBeanPostProcessors(beanFactory)向容器中注册所有的后置处理器，也就在这个过程中创建了AnnotationAwareAspectJAutoProxyCreator
 * -        3) 同样在refresh()方法中，会调用finishBeanFactoryInitialization(beanFactory)初始化剩下的单实例bean
 * -            a. 在finishBeanFactoryInitialization的过程中，创建业务逻辑组件和切面组件
 * -            b. 在创建业务逻辑组件和切面组件时，AnnotationAwareAspectJAutoProxyCreator会进行拦截创建过程
 * -            c. 在组件实例化前，AnnotationAwareAspectJAutoProxyCreator会调用postProcessBeforeInstantiation
 * -               检查组件是否需要增强，并将增强器封装在一个集合(List<Advisor> eligibleAdvisors)中
 * -            d. 接着，开始执行组件的构造方法，将bean真正的创建出来(详情可以参看 {@link MainConfigWithBeanPostProcessor})
 * -            e. 按照后置处理器类的执行流程，AnnotationAwareAspectJAutoProxyCreator依次又执行了postProcessAfterInstantiation和postProcessBeforeInitialization
 * -               但在这个两个方法中只是进行了一些判断，没有进行实质性的工作
 * -            f. 再接着AnnotationAwareAspectJAutoProxyCreator执行postProcessAfterInitialization。在这个方法中，判断组件是否需要增强
 * -               如果需要增强，将eligibleAdvisors依次织入业务逻辑组件，最终为IOC容器中创建一个业务逻辑组件的代理对象
 * -    6> 执行目标方法：
 * -        1) 通过IOC容器中的代理对象去执行目标方法
 * -        2) 执行过程中，通过CglibAopProxy.intercept()拦截：
 * -            a. 首先获得目标方法的拦截器链(chain, 拦截器链是由eligibleAdvisors[增强器]转换来的拦截器[MethodInterceptor]组成的)
 * -            b. 利用拦截器的链式调用机制(递归调用)，依次进入每一个拦截器进行执行
 * -            c. 最终呈现我们的执行结果：
 * -                方法正常执行：前置通过 -> 目标方法 -> 后置通知 -> 返回通知
 * -                方法出现异常：前置通过 -> 目标方法 -> 后置通知 -> 异常通知
 *
 * @author
 * @date 2018/12/24
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {

    /**
     * 将业务逻辑类加入到容器中
     *
     * @return
     */
    @Bean
    public MathCalculator mathCalculator() {
        return new MathCalculator();
    }

    /**
     * 将切面类加入到容器中
     *
     * @return
     */
    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }
}
