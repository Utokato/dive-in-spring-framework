1. web容器在启动的时候，会扫描每个jar包下的META-INF/services/javax.servlet.ServletContainerInitializer
2. 扫描后会加载这个文件的启动类 org.springframework.web.SpringServletContainerInitializer
3. spring的应用一启动就会加载感兴趣的WebApplicationInitializer接口下的所有组件(子类)
4. 并且为这些WebApplicationInitializer子类组件创建对象(如果组件不是接口，不是抽象类的话)
    1. AbstractContextLoaderInitializer：在这个类中onStartup方法执行过程中，创建根容器，createRootApplicationContext()
    2. AbstractDispatcherServletInitializer：在这个类的onStartup执行中：
        1. 创建一个web的IOC容器：createServletApplicationContext()
        2. 创建了一个前端总控制器：createDispatcherServlet(servletAppContext)
        3. 将创建好的DispatcherServlet添加到ServletAppContext容器中
            同时留下这个servlet的映射路径，让使用者来写：getServletMappings()
    3. AbstractAnnotationConfigDispatcherServletInitializer：注解方式配置的DispatcherServlet初始化器
        1. 创建根容器：createRootApplicationContext()
            通过getRootConfigClasses()获取根容器的配置文件，然后new AnnotationConfigWebApplicationContext()
            然后在new好的容器中设置配置文件
        2. 创建web的IOC容器：createServletApplicationContext()
            通过getServletConfigClasses()获取web容器的配置文件，然后new new AnnotationConfigWebApplicationContext()
            然后在new好的容器中设置配置文件
---
总结：
    以注解的方式来启动SpringMVC，继承AbstractAnnotationConfigDispatcherServletInitializer
    实现这个类中留给开发者的抽象方法，来指定DispatcherServlet的配置信息
    
---
定制SpringMVC:
1. @EnableWebMvc：开启SpringMVC的定制功能
    <mvc:annotation-driven />
2. 配置组件(视图解析器、视图映射、静态资源映射拦截器...)
    继承WebMvcConfigurer，实现其中的定制方法
               
    