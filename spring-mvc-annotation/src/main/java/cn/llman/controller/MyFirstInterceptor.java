package cn.llman.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author
 * @date 2018/12/28
 */
public class MyFirstInterceptor implements HandlerInterceptor {

    /**
     * 目标方法执行前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("The preHandle in MyFirstInterceptor is running." + " -> " + request.getRequestURI());
        return true;
    }

    /**
     * 目标方法执行后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("The postHandle in MyFirstInterceptor is running." + " -> " + request.getRequestURI());
    }

    /**
     * 页面响应以后执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("The afterCompletion in MyFirstInterceptor is running." + " -> " + request.getRequestURI());
    }
}
