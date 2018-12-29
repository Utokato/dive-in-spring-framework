package cn.llman.config;

import cn.llman.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 使用@PropertySource {@link PropertySource} 来加载外部的配置文件
 * 将配置文件中的属性以key:value的形式加载到运行环境中(environment of application context)
 * 然后，在需要的位置使用 ${} 的形式去取出需要的值
 *
 * @author
 * @date 2018/12/21
 */
@Configuration
@PropertySource(value = {"classpath:/person.properties"})
@PropertySources(value = {
        @PropertySource(value = {""}),
        @PropertySource(value = {""}),
        @PropertySource(value = {""})
})
public class MainConfigPropertyValue {

    @Bean
    public Person person() {
        return new Person();
    }

}
