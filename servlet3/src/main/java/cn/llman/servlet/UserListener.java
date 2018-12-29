package cn.llman.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听项目的启动和停止
 *
 * @author
 * @date 2018/12/28
 */
public class UserListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        System.out.println("The contextInitialized of UserListener is running.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("The contextDestroyed of UserListener is running.");
    }
}
