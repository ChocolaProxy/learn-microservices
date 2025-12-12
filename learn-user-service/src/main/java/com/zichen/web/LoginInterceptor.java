package com.zichen.web;

import com.zichen.pojo.LoginUser;
import com.zichen.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Object obj = request.getSession().getAttribute(AuthService.SESSION_USER_KEY);
        if (obj == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"ok\":false,\"msg\":\"未登录\"}");
            return false;
        }

        // 可选：把当前用户放到 request 里，后面controller直接取
//        request.setAttribute("LOGIN_USER", (LoginUser) obj);
        return true;
    }
}

