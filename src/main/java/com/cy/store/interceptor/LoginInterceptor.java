package com.cy.store.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 定义一个拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 检测全局session对象中是否有uid的数据，有则放行，无则重定向到登录页面
     *
     * @param request  请求对象
     * @param response 相应对象
     * @param handler  处理器（url+Controller:映射）
     * @return 如果返回值为false，拦截；如果返回值为true，放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object uid = request.getSession().getAttribute("uid");
        if (uid == null) {
            /**拦截
             * 并重定向到 web/login.html
             * 返回false，结束后续的操作
             */
            response.sendRedirect("/web/login.html");
            return false;
        }
        //放行
        return true;
    }
}
