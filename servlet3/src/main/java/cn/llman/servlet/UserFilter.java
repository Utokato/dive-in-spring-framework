package cn.llman.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author
 * @date 2018/12/28
 */
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 过滤请求
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("The method named doFilter of UserFilter is running.");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
