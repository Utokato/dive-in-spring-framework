package cn.llman.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 声明式事务：
 * <p>
 * <p>
 * 环境搭建：
 * 1. 导入依赖：数据源，数据库驱动，springJDBC模块
 * 2. 配置数据源、JdbcTemplate(Spring提供的简化数据库操作的工具)操作数据库
 * 3. 使用@Transactional标注，当前方法或者类中的方法支持事务
 * 4. 在配置类中使用@EnableTransactionManagement 开启基于注解的事务管理功能
 * 5. 配置事务管理器(PlatformTransactionManager)，来控制事务
 *
 * @author
 * @date 2018/12/25
 */
@EnableTransactionManagement
@Configuration
@ComponentScan({"cn.llman.tx"})
@PropertySource({"classpath:/dbconfig.properties"})
public class TxConfig {

    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;
    @Value("${db.driverClass}")
    private String driverClass;

    private String jbdcUrl = "jdbc:mysql://localhost:3306/test";

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jbdcUrl);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        // Spring 对@Configuration的配置类有特殊的处理
        // 给容器中加组件的方法，在之后的多次调用中都只是从容器中找组件，并不会重复往容器中加组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    /**
     * 在容器中注册事务管理器
     * 事务管理器中需要配置数据源(DataSource)
     *
     * @return
     * @throws PropertyVetoException
     */
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }
}
