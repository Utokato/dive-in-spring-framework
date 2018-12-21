package cn.llman.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * 定义一个animal对象的工厂bean
 *
 * @author
 * @date 2018/12/19
 */
public class AnimalFactoryBean implements FactoryBean<Animal> {

    /**
     * 返回一个animal对象，并添加到容器中
     *
     * @return
     * @throws Exception
     */
    @Override
    public Animal getObject() throws Exception {
        System.out.println("Creating a animal ...");
        return new Animal();
    }

    @Override
    public Class<?> getObjectType() {
        return Animal.class;
    }

    /**
     * 该实例是否为单例?
     * true: 是单实例
     * false: 不是单实例
     *
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }
}
