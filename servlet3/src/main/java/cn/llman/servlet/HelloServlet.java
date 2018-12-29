package cn.llman.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 原始的servlet3.0开发
 * {@link WebServlet}
 * {@link WebFilter}
 * {@link WebListener}
 *
 * @author
 * @date 2018/12/28
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(Thread.currentThread() + " start... ");
        try {
            sayHello();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resp.getWriter().write("Hello, world");
        System.out.println(Thread.currentThread() + " finished... ");
    }

    private void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread() + " processing... ");
        Thread.sleep(3000);
    }
}
