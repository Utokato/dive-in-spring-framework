package cn.llman.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author
 * @date 2018/12/28
 */
@WebServlet(value = {"/async"}, asyncSupported = true)
public class HelloAsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 支持异步servlet支持：asyncSupported = true
        // 2. 开启异步
        System.out.println("Main thread started: " + Thread.currentThread() + " -> " + System.currentTimeMillis());
        AsyncContext asyncContext = req.startAsync();
        // 3. 业务逻辑进行异步处理，开始异步处理
        asyncContext.start(() -> {
            try {
                System.out.println("Sub thread started: " + Thread.currentThread() + " -> " + System.currentTimeMillis());
                sayHello();
                asyncContext.complete();
                ServletResponse response = asyncContext.getResponse();
                response.getWriter().write("Hello, AsyncServlet");
                System.out.println("Sub thread finished: " + Thread.currentThread() + " -> " + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("Main thread finished: " + Thread.currentThread() + " -> " + System.currentTimeMillis());
    }

    private void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread() + " processing... " + " -> " + System.currentTimeMillis());
        Thread.sleep(3000);
    }
}
