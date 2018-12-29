package cn.llman.config;

import cn.llman.bean.Daffodil;
import cn.llman.bean.Jasmine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @date 2018/12/29
 */
@Configuration
public class MainConfigOfAware {

    @Bean
    public Jasmine jasmine() {
        Jasmine jasmine = new Jasmine();
        jasmine.setBeanName("myJasmine");
        jasmine.setName("jasmine");
        jasmine.setNum(10);
        return jasmine;
    }

    @Bean
    public Daffodil daffodil() {
        Daffodil daffodil = new Daffodil();
        daffodil.setName("daffodil");
        daffodil.setNum(11);
        return daffodil;
    }

}

