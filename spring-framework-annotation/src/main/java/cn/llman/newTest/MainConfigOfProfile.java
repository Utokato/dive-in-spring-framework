package cn.llman.newTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import sun.util.resources.cldr.ewo.CalendarData_ewo_CM;

/**
 * @author
 * @date 2019/2/26
 */
@Profile({"test"})
@PropertySource({"classpath:/person.properties"})
@Configuration
public class MainConfigOfProfile {

    @Bean
    public Car car() {
        return new Car();
    }

    @Bean
    public Father father() {
        return new Father();
    }

    @Bean
    @Profile({"prod"})
    public Child child() {
        return new Child();
    }

}
