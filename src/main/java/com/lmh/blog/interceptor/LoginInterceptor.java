package com.lmh.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lvmen on 2019/11/18
 */
public class LoginInterceptor implements HandlerInterceptor { // 拦截器相当于一张渔网

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin"); // 重定向到登录页面
        }
        return true; // 继续往下执行
    }
}
