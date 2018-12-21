package cn.llman.condition;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author
 * @date 2018/12/19
 */
public class WindowsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 获取当前运行的环境，JVM OS 等
        Environment environment = context.getEnvironment();
        String osName = environment.getProperty("os.name");
        if (osName.contains("Windows")) {
            // 判断是否为Windows系统
            return true;
        }
        return false;
    }
}
