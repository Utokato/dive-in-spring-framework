package cn.llman.config;

import cn.llman.bean.Basketball;
import cn.llman.bean.BasketballBeanPostProcessor;
import cn.llman.bean.FootballFactoryBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;

/**
 * 总结Spring的可扩展点：
 * 1. {@link BeanFactoryPostProcessor}
 * 2. {@link InitializingBean#afterPropertiesSet()} 和 {@link DisposableBean#destroy()}
 * 3. {@link Bean#initMethod()} 和 {@link Bean#destroyMethod()}
 * 4. {@link BeanPostProcessor} 和 {@link InstantiationAwareBeanPostProcessor}
 * 5. {@link FactoryBean#getObject()}
 *
 * @author lma
 * @date 2019/08/08
 */
@Configurable
public class MainConfigNew {
    @Bean
    public FootballFactoryBean footballFactoryBean() {
        return new FootballFactoryBean();
    }

    @Bean(value = "basketball", initMethod = "initCustom", destroyMethod = "destroyCustom")
    public Basketball basketball() {
        return new Basketball(1, 35);
    }

    @Bean
    public BasketballBeanPostProcessor basketballBeanPostProcessor() {
        return new BasketballBeanPostProcessor();
    }

}
