package cn.llman.test;

import cn.llman.tx.TxConfig;
import cn.llman.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author
 * @date 2018/12/25
 */
public class TestTx {

    @Test
    public void testTxOne() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = applicationContext.getBean(UserService.class);

        userService.insertUser();

        applicationContext.close();

    }
}
