package cn.llman.newTest;

import cn.llman.bean.Cat;
import cn.llman.ext.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@ComponentScan(basePackages = {"cn.llman"},
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION,
                        classes = {Controller.class}),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = {Cat.class})
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION,
                        classes = {Service.class}),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = {UserService.class})
        }
)
@ComponentScans(value = {
        @ComponentScan(),
        @ComponentScan(),
        @ComponentScan()
})
@Configuration
public class MainConfig {
}
