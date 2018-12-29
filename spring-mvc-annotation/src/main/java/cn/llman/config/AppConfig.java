package cn.llman.config;

import cn.llman.controller.MyFirstInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

/**
 * web容器配置文件
 * 配置SpringMVC只扫描{@link Controller}注解的组件
 * useDefaultFilters = false 禁用默认的过滤规则
 *
 * @author
 * @date 2018/12/28
 */
@ComponentScan(value = {"cn.llman"}, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
}, useDefaultFilters = false)
@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    /**
     * 定制视图解析器
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // 默认jsp("/WEB-INF/", ".jsp")，即从/WEB-INF/找.jsp文件
        // registry.jsp();
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    /**
     * 定制静态资源访问
     * 相当于xml配置文件中的：<mvc:default-servlet-handler />
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 定制添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns("/**");
    }
}
