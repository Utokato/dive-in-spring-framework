### Dive in Spring Framework

跟着《尚硅谷》的一个课程学习，课程名称是---《Spring-framework 源码深度解析》

---

主要的学习记录都在 cn.llman.config 文件夹中

由于是在IDEA中写的，所以用了很多{@link}的注释，方便源码的查看，所以建议使用IDEA打开食用

---

其中，较为难理解的，或者说较为复杂的是利用Spring实现的Aop机制

所以，这部分的内容要先理解大体的思路再进去看源码，不然有点晕

Spring AOP 的大体思路是：

1. 通过@EnableAspectJAutoProxy注解开启AOP功能支持，这个注解是一个组合注解，其中最重要的当属@Import注解

   @Import最终会向IOC容器中注册一个Bean(AnnotationAwareAspectJAutoProxyCreator)

2. 通过查看AnnotationAwareAspectJAutoProxyCreator的继承关系，可以得知这是一个InstantiationAwareBeanPostProcessor类型的后置处理器，这种类型的处理器和它的父类中总共定义了4个方法

3. 这4个方法，分别对应了Spring Bean产生过程中的两个阶段(实例化和初始化)

4. 在4个方法的拦截和包装下，将通知方法包装在了业务逻辑组件中，最终向IOC容器中返回一个业务逻辑组件的代理对象

   代理技术可以是JDK的动态代理，也可以是CGLib的代理技术

5. Bean的代理对象在IOC容器中创建完毕后，每次从容器中获取的都是代理对象。代理对象在执行逻辑方法时，会将逻辑方法和通知方法组装成一个执行链(Chain)，然后通过链式调用(递归调用)的过程，保证了通知方法和逻辑方法的执行顺序

大致就是上面的，分为两个阶段：

- 代理对象的产生
  - 后置处理器的注册
  - 在后置处理器的帮助下产生代理对象
- 代理对象执行方法