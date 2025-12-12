package com.zichen.controller;

import com.zichen.config.OnlineUserStore;
import com.zichen.pojo.LoginReq;
import com.zichen.pojo.LoginUser;
import com.zichen.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;
    private final OnlineUserStore onlineUserStore;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginReq req, HttpSession session, HttpServletRequest request) {
        LoginUser user = authService.login(req, session);

        // ⭐ 核心一句：登录成功后换 sessionId
        request.changeSessionId();

        return Map.of(
                "ok", true,
                "sessionId", session.getId(),
                "user", user
        );
    }

    @GetMapping("/me")
    public LoginUser me(HttpSession session) {
        return authService.currentUser(session);
    }

    @GetMapping("/online-users")
    public Map<String, Object> onlineUsers() {
        return Map.of(
                "ok", true,
                "count", onlineUserStore.count(),
                "users", onlineUserStore.list()
        );
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        authService.logout(session);
        return Map.of("ok", true);
    }
}
