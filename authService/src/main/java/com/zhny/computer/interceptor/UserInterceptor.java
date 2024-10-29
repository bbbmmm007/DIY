package com.zhny.computer.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // 检查用户是否登录
        if (username == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect("/web/login.html");
            return false; // 中断请求
        }

        return true; // 继续执行请求
    }
}
