package cn.llman.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * 根容器配置文件
 * 配置Spring不扫描{@link Controller}注解的组件
 *
 * @author
 * @date 2018/12/28
 */
@ComponentScan(value = {"cn.llman"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
@Configuration
public class RootConfig {

}
