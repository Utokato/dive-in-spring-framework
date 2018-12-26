package cn.llman.itest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bean 的 Instantiation 实例化 Initialization 初始化
 * <p>
 * 这两者之间的区别
 * <p>
 * 这里需要区分的是 JDK的Initialization 初始化 和 Spring的Initialization 初始化
 * <p>
 * JDK中：初始化(Initialization)是指类文件(.class)的初始化，这属于虚拟机类加载机制的范畴
 * -    在Java中，一个对象在可以被使用之前，必须被正确初始化，这一点是Java规范规定的
 * SpringCore：Bean的实例化(Instantiation)和初始化(Initialization)都是在类文件的初始化之后的
 * -    SpringBean的初始化强调：经过实例化以后的Bean的初始化
 * -    为什么实例化以后的Bean还需要初始化呢?
 * -    @Bean {@link Bean} 中可以定义bean在构造函数结束后的初始化(Initialization)方法
 * -    从这一点，更是能够看出来SpringBeanInitialization和JDKInitialization完全不是一回事
 * <p>
 * - Notice! A method before instantiation is running, and the bean prepared to instance is: book
 * - Bingo! The constructor of book is running.
 * - Fortunately! I am the method after instantiation. I can get a book that is: Book[name='A Tale of Two Cities', page=599]
 * - Notice! I am the method before initialization. I can also get a book that is: Book[name='A Tale of Two Cities', page=599]
 * - Try to work with init method.
 * - Ooh! Fortunately! I am the method after initialization, so I can get a book that is: Book[name='War and Peace', page=499]
 * - Eureka! I have found a book that detail is: Book[name='Pride and Prejudice', page=699]
 * - 上面是测试方法在控制台输入的日志，从日志中就可以看到整个SpringBean的实例化、初始化和BeanPostProcessor的工作机制：
 * -    1> 首先是postProcessBeforeInstantiation运行，此时bean还没有实例化，所以只能获得bean的名字和类文件
 * -        注意：此时已经能够正常获得相应的class文件，说明JDK的类文件初始化已经结束，所以最早的应该是JVM对类文件的加载
 * -    2> 之后，Spring通过该类的构造器(constructor)实例化对象
 * -    3> postProcessAfterInstantiation运行，此时实例化对象完毕，所以可以获得bean的实例对象
 * -        打印出Book[name='A Tale of Two Cities', page=599]，这也是在构造函数中设置的
 * -    4> postProcessBeforeInitialization运行，此时实例化以及结束，但SpringBean的初始化还没有开始
 * -        所以获得的book实例为：Book[name='A Tale of Two Cities', page=599]
 * -    5> 然后，指定的初始化方法开始运行，在初始化方法中，我们对书籍的信息进行了修改，从《双城记》修改到了《战争与和平》
 * -    6> postProcessAfterInitialization运行，此时SpringBean的实例化和初始化工作都已结束
 * -        并且由于初始化中做了一些修改，所以这里获得的bean为：Book[name='War and Peace', page=499]
 * -        同时，在这个方法中也可以获得Book对象，我们再次对这个bean进行修改，从《战争与和平》修改到《傲慢与偏见》
 * -    7> 此时，SpringBean经历了上面的流程最终加入了IOC容器中了
 * -    8> 最后，我们在测试方法中，从IOC容器中获取该实例，猜猜我们拿到了什么实例?
 * -        没错，Book[name='Pride and Prejudice', page=699]，就是《傲慢与偏见》了
 *
 * @author
 * @date 2018/12/25
 */
@Configuration
@ComponentScan({"cn.llman.itest"})
public class BookConfig {

    @Bean(initMethod = "init")
    public Book book() {
        Book book = new Book("A Tale of Two Cities", 599);
        return book;
    }

}
