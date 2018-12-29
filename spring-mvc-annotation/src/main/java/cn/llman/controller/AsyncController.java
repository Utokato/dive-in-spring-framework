package cn.llman.controller;

import cn.llman.bean.MyDeferredResult;
import cn.llman.service.DeferredResultQueue;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author
 * @date 2018/12/28
 */
@Controller
public class AsyncController {

    @RequestMapping("/createOrder")
    @ResponseBody
    public DeferredResult<Object> createOrder() {
        DeferredResult<Object> deferredResult = new DeferredResult<>((long) 3000, "Create order failed.");
        DeferredResultQueue.save(new MyDeferredResult(deferredResult, System.currentTimeMillis()));
        return deferredResult;
    }

    @RequestMapping("/create")
    @ResponseBody
    public String create() {
        String orderId = "null";
        while (!DeferredResultQueue.isEmpty()) {
            MyDeferredResult myDeferredResult = DeferredResultQueue.get();
            if ((System.currentTimeMillis() - myDeferredResult.getFlag()) > 3000) {
                System.out.println("Gotten a overdue request, and throw it away.");
                continue;
            }
            orderId = UUID.randomUUID().toString();
            DeferredResult deferredResult = myDeferredResult.getDeferredResult();
            deferredResult.setResult(orderId);
            break;
        }
        return "success -> " + orderId;
    }

    /**
     * 1. 控制器返回Callable
     * 2. Spring异步处理，将Callable提交到{@link TaskExecutor}，使用一个隔离的线程进行执行
     * 3. DispatcherServlet和所有的filters退出web容器线程，但是response仍然保持打开的状态
     * 4. Callable返回结果，SpringMVC将再次向servlet容器发送请求
     * 5. 根据Callable的返回结果，DispatcherServlet继续进行试图渲染等流程(从请求到视图渲染)
     * <p>
     * ----------- 请求发送过来，主线程开始处理 ---------------
     * The preHandle in MyFirstInterceptor is running. -> /asyncOne
     * Main thread started: Thread[http-nio-8090-exec-4,5,main] -> 1545998120125
     * Main thread finished: Thread[http-nio-8090-exec-4,5,main] -> 1545998120125
     * ----------- 主线程处理结束，DispatcherServlet和filters主线程 ---------------
     * <p>
     * ----------- Callable在副线程开始处理 ---------------
     * Sub thread started: Thread[MvcAsync1,5,main] -> 1545998120140
     * Sub thread finished: Thread[MvcAsync1,5,main] -> 1545998123140
     * ----------- Callable在副线程处理结束，返回处理结果 ---------------
     * <p>
     * ----------- 根据Callable返回处理结果，再次向容器发送请求 ---------------
     * The preHandle in MyFirstInterceptor is running. -> /asyncOne
     * The postHandle in MyFirstInterceptor is running. -> /asyncOne
     * The afterCompletion in MyFirstInterceptor is running. -> /asyncOne
     * ----------- 异步处理全部结束 ---------------
     * <p>
     * <p>
     * 异步拦截器：
     * -    1. 原生API的AsyncListener
     * -    2. SpringMVC的AsyncHandlerInterceptor
     *
     * @return
     */
    @RequestMapping("/asyncOne")
    @ResponseBody
    public Callable<String> asyncOne() {
        System.out.println("Main thread started: " + Thread.currentThread() + " -> " + System.currentTimeMillis());
        Callable<String> stringCallable = () -> {
            System.out.println("Sub thread started: " + Thread.currentThread() + " -> " + System.currentTimeMillis());
            Thread.sleep(3000);
            System.out.println("Sub thread finished: " + Thread.currentThread() + " -> " + System.currentTimeMillis());
            return "Callable<String> asyncOne()";
        };
        System.out.println("Main thread finished: " + Thread.currentThread() + " -> " + System.currentTimeMillis());
        return stringCallable;

    }
}
