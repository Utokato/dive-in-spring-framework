package cn.llman.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * 日志切面类
 * <p>
 * {@link Aspect} 声明这是一个切面类
 *
 * @author
 * @date 2018/12/24
 */
@Aspect
public class LogAspects {

    /**
     * {@link Pointcut} 抽取公共的切入点表达式
     * 1> 本类引用：直接使用方法名
     * 2> 其他的切面类引用：使用全方法名(全类名+方法名)
     */
    @Pointcut("execution(public * cn.llman.aop.MathCalculator.*(..))")
    public void pointCut() {
    }


    /**
     * {@link Before} 在目标方法运行之前切入
     * -    通过切点表达式来指定在哪个方法处进行切入
     */
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println(methodName + " is preparing to running, the args of this function are {" + Arrays.asList(args) + "}");
    }

    /**
     * {@link After} 在目标方法结束之后切入
     * -    无论方法正常还是异常结束，都会执行该方法
     */
    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() +
                " has finished, the args of this function are {"
                + Arrays.asList(joinPoint.getArgs()) + "}");
    }

    /**
     * {@link AfterReturning} 在方法正常返回之后切入
     *
     * @param joinPoint 这个参数必须出现在参数列表的第一位
     * @param result
     */
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println(joinPoint.getSignature().getName() +
                " has a correct result, and this is {" + result + "}");
    }

    /**
     * {@link AfterThrowing} 在方法出现异常之后切入
     */
    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        System.out.println(joinPoint.getSignature().getName() + " met a exception that is {" + exception + "}");
    }
}
