package cn.llman.config;

import cn.llman.bean.Person;
import cn.llman.service.BookService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * 配置类 == 配置文件
 * {@link Configuration}
 * {@link Component}
 * {@link ComponentScan}
 * {@link ComponentScans}
 *
 * @author
 * @date 2018/12/18
 */
@Configuration //告诉Spring这是一个配置类(范式注解)
/*@ComponentScan(basePackages = {"cn.llman"}, useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class})
        }, excludeFilters = {

})*/
@ComponentScans(value = {
        @ComponentScan(basePackages = {"cn.llman"}, useDefaultFilters = false,
        includeFilters = {
                // @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
                // @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class}),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
        }, excludeFilters = {

})
})
// @ComponentScan  value:指定要扫描的包
// excludeFilters = Filter[] ：指定扫描的时候按照什么规则排除那些组件
// includeFilters = Filter[] ：指定扫描的时候只需要包含哪些组件
// FilterType.ANNOTATION：按照注解 ***
// FilterType.ASSIGNABLE_TYPE：按照给定的类型 ***
// FilterType.ASPECTJ：使用ASPECTJ表达式
// FilterType.REGEX：使用正则指定
// FilterType.CUSTOM：使用自定义规则 ***
public class MainConfig {

    // 给容器中注册一个Bean;类型为返回值的类型，id默认是用方法名作为id
    @Bean
    public Person person() {
        return new Person("lma", 21);
    }

}
