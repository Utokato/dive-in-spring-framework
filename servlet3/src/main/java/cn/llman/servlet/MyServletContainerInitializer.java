package cn.llman.servlet;

import cn.llman.service.HelloService;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

/**
 * 容器启动的时候，会将{@link HandlesTypes}指定的这个类型下面的子类(实现类，子接口等)传递过来
 * 在@HandlesTypes中传入感兴趣的类型，如：HelloService
 *
 * @author
 * @date 2018/12/28
 */
@HandlesTypes(value = {HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    /**
     * 应用启动的时候，会运行onStartup方法
     * <p>
     * 1. 使用ServletContext注册Web组件(Servlet、Filter、Listener)
     * 2. 使用编码的方式，在项目启动的时候给ServletContext中添加组件
     * -    必须在项目启动的时候来添加
     * -    在什么位置能提起获取这个ServletContext，来添加组件呢?
     * -        1) 实现了ServletContainerInitializer接口，在onStartup方法中可以获取
     * -        2) 实现了ServletContextListener接口，在contextInitialized方法中可以获取ServletContextEvent事件，
     * -            通过这个事件也能获取ServletContext
     * -    注意：出于安全，容器一旦启动，就不能通过ServletContext向容器中注册组件了
     *
     * @param c   上面所说的感兴趣类型的子类型类文件集合
     * @param ctx 代表当前Web应用的ServletContext；一个Web应用对应一个ServletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("The interest types are: ");
        c.forEach(System.out::println);

        // 以Java代码的方式注册组件
        ServletRegistration.Dynamic userServlet = ctx.addServlet("userServlet", new UserServlet());
        // 配置servlet的映射信息
        userServlet.addMapping("/user");

        ctx.addListener(UserListener.class);

        FilterRegistration.Dynamic userFilter = ctx.addFilter("userFilter", UserFilter.class);
        userFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

    }
}
