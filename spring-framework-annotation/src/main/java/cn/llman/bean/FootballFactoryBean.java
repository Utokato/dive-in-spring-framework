package cn.llman.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author lma
 * @date 2019/08/08
 */
public class FootballFactoryBean implements FactoryBean<Football> {
    @Override
    public Football getObject() throws Exception {
        return new Football(1, 32);
    }

    @Override
    public Class<?> getObjectType() {
        return Football.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
