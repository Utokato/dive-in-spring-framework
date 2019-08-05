package cn.llman.tx;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.lang.reflect.Method;

/**
 * 声明式事务：
 * <p>
 * <p>
 * 环境搭建：
 * 1. 导入依赖：数据源，数据库驱动，springJDBC模块
 * 2. 配置数据源、JdbcTemplate(Spring提供的简化数据库操作的工具)操作数据库
 * 3. 使用@Transactional标注，当前方法或者类中的方法支持事务
 * 4. 在配置类中使用@EnableTransactionManagement 开启基于注解的事务管理功能
 * 5. 配置事务管理器(PlatformTransactionManager)，来控制事务
 * <p>
 * 原理：
 * 1. @EnableTransactionManagement 是一个组合注解，其中最重要的注解为：@Import(TransactionManagementConfigurationSelector.class)
 * 2. 使用TransactionManagementConfigurationSelector给容器中导入组件
 * -    在@EnableTransactionManagement定义了激活模式是AdviceMode.PROXY，所以在Selector中给组件导入的是2个组件：
 * -        AutoProxyRegistrar 和 ProxyTransactionManagementConfiguration
 * 3. {@link AutoProxyRegistrar} 中调用 registerBeanDefinitions 方法,
 * -    给容器中注册：{@link InfrastructureAdvisorAutoProxyCreator} 组件
 * -    InfrastructureAdvisorAutoProxyCreator本质上也是一个后置处理器，这与实现AOP的AnnotationAwareAspectJAutoProxyCreator机制一样
 * -    利用后置处理器的机制在bean创建以后，对bean进行包装，最终向IOC容器中返回一个代理对象(包含了增强器)
 * -    代理对象执行方法时，利用链式调用机制进行递归调用
 * 4. {@link ProxyTransactionManagementConfiguration}
 * -    1> 向容器中注册事务增强器(transactionAdvisor)
 * -        a. advisor需要设置事务属性源，事务属性源同样由一个bean来提供，即AnnotationTransactionAttributeSource，在这个bean中有很多与事务解析相关的信息
 * -        b. advisor需要设置事务拦截器，事务拦截器同样由一个bean来提供，即TransactionInterceptor
 * -                这个拦截器中，同时又保存了上面的事务属性源(TransactionAttributeSource)和事务管理器(TransactionManager)
 * -                TransactionInterceptor本质上是一个MethodInterceptor(方法拦截器)
 * -                拦截器在目标方法执行的时候：
 * -                    执行拦截器链：这个拦截器链就只有一个拦截器，即：TransactionInterceptor(事务拦截器)
 * -                    事务拦截器的工作机制：
 * -                    {@link TransactionAspectSupport#invokeWithinTransaction(Method, Class, TransactionAspectSupport.InvocationCallback)}
 * -                        1) 先获取事务相关的属性，如果为空的话，表示这个方法没有事务
 * -                        2) 再获取PlatformTransactionManager(平台事务管理器)
 * -                            如果事先没有添加指定任何TransactionManager，最终会从容器中根据类型获取一个PlatformTransactionManager
 * -                            所以，我们可以在配置类中以@Bean的方式向IOC容器中加入一个PlatformTransactionManager，{@link #transactionManager()}
 * -                        3) 执行目标方法
 * -                            如果发生异常，获取到事务管理器，利用事务管理器回滚本次操作
 * -                            如果正常执行，同样获取到事务管理器，利用事务管理器提交本次操作
 */
@EnableTransactionManagement
@Configuration
@ComponentScan({"cn.llman.tx"})
@PropertySource({"classpath:/dbconfig.properties"})
public class TxConfig {

    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;
    @Value("${db.driverClass}")
    private String driverClass;

    private String jbdcUrl = "jdbc:mysql://localhost:3306/test";

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jbdcUrl);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        // Spring 对@Configuration的配置类有特殊的处理
        // 给容器中加组件的方法，在之后的多次调用中都只是从容器中找组件，并不会重复往容器中加组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    /**
     * 在容器中注册事务管理器
     * 事务管理器中需要配置数据源(DataSource)
     *
     * @return
     * @throws PropertyVetoException
     */
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }
}
