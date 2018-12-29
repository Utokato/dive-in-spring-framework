package cn.llman.service;

import cn.llman.bean.MyDeferredResult;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author
 * @date 2018/12/28
 */
public class DeferredResultQueue {

    private static Queue<MyDeferredResult> queue = new ConcurrentLinkedQueue<>();

    public static void save(MyDeferredResult myDeferredResult) {
        queue.add(myDeferredResult);
    }

    public static MyDeferredResult get() {
        return queue.poll();
    }

    public static boolean isEmpty() {
        return queue.isEmpty();
    }
}
