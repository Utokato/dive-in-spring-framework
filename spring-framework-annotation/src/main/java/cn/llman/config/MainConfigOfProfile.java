package cn.llman.config;

import cn.llman.bean.Color;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;

/**
 * Profile: {@link Profile}
 * -    Spring提供的，可以根据当前环境，动态地激活或切换一系列组件的功能；
 * <p>
 * 如：开发环境，测试环境，生产环境
 * 数据源：(/A),(/B),(/C) ;
 * <p>
 * -    @Profile: 指定组件在哪种情况下才能被注册到容器中，不指定的情况下，默认任何情况下所有组件都可以注册到IOC容器中
 * -    1> 加了@Profile注解的bean，只有在指定的环境下才能加载到IOC容器中。默认环境是：default
 * -    2> 加了@Profile注解的配置类，只有指定的环境下，整个配置类的所有配置才能生效。
 * -    3> 没有加@Profile注解的bean，在任何环境下都是默认加载的
 *
 */
@Profile("test")
@PropertySource({"classpath:/dbconfig.properties"})
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    private StringValueResolver stringValueResolver;

    private String driverClass;

    private String jbdcUrl = "jdbc:mysql://localhost:3306/test";

    // @Profile("test")
    @Bean("color")
    public Color color() {
        return new Color();
    }


    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}") String password) throws Exception {
        return getDataSource(password);
    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDevelopment(@Value("${db.password}") String password) throws Exception {
        return getDataSource(password);
    }

    @Profile("pro")
    @Bean("proDataSource")
    public DataSource dataSourceProduct(@Value("${db.password}") String password) throws Exception {
        return getDataSource(password);
    }

    private DataSource getDataSource(@Value("${db.password}") String password) {
        return getDataSource(password, user, driverClass, jbdcUrl);
    }

    public static DataSource getDataSource(@Value("${db.password}") String password, String user, String driverClass, String jbdcUrl) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jbdcUrl);
        return dataSource;
    }

    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;
        this.driverClass = stringValueResolver.resolveStringValue("${db.driverClass}");
    }
}
