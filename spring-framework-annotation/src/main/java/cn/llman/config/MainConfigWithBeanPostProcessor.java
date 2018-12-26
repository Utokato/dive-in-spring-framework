package cn.llman.config;

import cn.llman.bean.Animal;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bean的实例化(Instantiation)和初始化(Initialization):
 * -    实例化：一般是由类创建对象，在构造函数中需要向内存中开辟空间
 * -    初始化：在实例化的基础上，对对象中的值进行初始赋值
 * <p>
 * 在Spring IOC 容器中，有两种类型的BeanPostProcessor，分别是：{@link BeanPostProcessor} 和 {@link InstantiationAwareBeanPostProcessor}
 * 这两种BeanPostProcessor分别对应了上面的bean创建过程中的两个阶段：
 * -    {@link BeanPostProcessor}                       对应了 bean的初始化
 * -        包含了两个方法：
 * -            postProcessBeforeInitialization     bean 初始化前执行
 * -            postProcessAfterInitialization      bean 初始化后执行
 * -
 * -    {@link InstantiationAwareBeanPostProcessor}     对应了bean的实例化
 * -        InstantiationAwareBeanPostProcessor 原本也是 BeanPostProcessor的子类，但它额外包含了两个方法：
 * -            postProcessBeforeInstantiation      bean 实例化前执行
 * -            postProcessAfterInstantiation       bean 实例化后执行
 * <p>
 * -    所以，以Animal类为例，所有方法的执行顺序如下：
 * -        Some handles before instantiation, ... animal --> class cn.llman.bean.Animal
 * -        The constructor of Animal is running...
 * -        Some handles after instantiation, ... animal --> cn.llman.bean.Animal@661972b0
 * -        Some handles before initialization, ... animal --> cn.llman.bean.Animal@661972b0
 * -        Some handles after initialization, ... animal --> cn.llman.bean.Animal@661972b0
 *
 * @author
 * @date 2018/12/25
 */
@Configuration
@ComponentScan({"cn.llman.bean"})
public class MainConfigWithBeanPostProcessor {
    @Bean
    public Animal animal() {
        return new Animal();
    }
}
