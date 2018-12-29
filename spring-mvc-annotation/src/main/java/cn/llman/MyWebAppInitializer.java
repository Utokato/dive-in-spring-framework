package cn.llman;

import cn.llman.config.AppConfig;
import cn.llman.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * web 容器在启动创建对象的时候，就会调用该类的方法来初始化容器和前端控制器
 *
 * @author
 * @date 2018/12/28
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 获取根容器的配置类
     * 相当于以前Spring的配置文件
     * 用于创建根容器(父容器)
     *
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * 获取web容器的配置类
     * 相当于以前的SpringMVC的配置文件
     * 用于创建web容器(子容器)
     *
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    /**
     * 获取DispatcherServlet的映射信息
     * / : 拦截所有请求(包括静态资源，xxx.js，xxx.png ..)，但不包括 *.jsp
     * /* : 拦截所有请求，包括 *.jsp
     * jsp页面是tomcat的jsp引擎解析
     *
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
