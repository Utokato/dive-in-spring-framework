package cn.llman.newTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author
 * @date 2019/2/26
 */
@Configuration
@PropertySource("classpath:/person.properties")
public class MainConfig3 {

    @Bean
    public Father father(){
        return new Father();
    }

    @Bean
    public Child child(){
        return new Child();
    }
}
