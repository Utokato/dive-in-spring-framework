package cn.llman.bean;

import lombok.*;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author lma
 * @date 2019/08/08
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Basketball implements InitializingBean, DisposableBean {
    private int id;
    private int size;

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean.destroy() -> invoked");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean.afterPropertiesSet() -> invoked");
    }

    private void initCustom() {
        System.out.println("user-defined.initCustom() -> invoked");
    }

    private void destroyCustom() {
        System.out.println("user-defined.destroyCustom() -> invoked");
    }
}
