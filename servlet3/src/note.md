Shared libraries(共享库) / Runtimes pluggability(运行时插件)

1. Servlet容器启动时会自动扫描当前应用里面的一个jar包
    ServletContainerInitializer的实现
2. 提供一个ServletContainerInitializer的实现类
    并绑定在META-INF/services/javax.servlet.ServletContainerInitializer文件中
    在这文件的内容中，指向了ServletContainerInitializer的实现类的全类名
---
总结：
    容器在启动的时候，会扫描当前应用的每一个jar里面META-INF/services/javax.servlet.ServletContainerInitializer指定的实现类
    启动，并运行这个实现类的方法。
    同时，可以通过@HandlesTypes注解传入感兴趣的类型
    
ServletContainerInitializer
@HandlesTypes