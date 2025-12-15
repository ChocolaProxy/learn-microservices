package com.zichen.controller;


import com.zichen.pojo.JwtLoginReq;
import com.zichen.pojo.JwtLoginResp;
import com.zichen.pojo.JwtUser;
import com.zichen.service.JwtAuthService;
import com.zichen.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtAuthController {

    private final JwtAuthService jwtAuthService;
    private final JwtTokenService jwtTokenService;

    /**
     * JWT 登录
     */
    @PostMapping("/login")
    public JwtLoginResp login(@RequestBody JwtLoginReq req) {
        JwtUser user = jwtAuthService.login(req);
        String token = jwtTokenService.generate(user);

        return new JwtLoginResp(
                token,
                "Bearer",
                user
        );
    }

    /**
     * 获取当前登录用户（JWT）
     */
    @GetMapping("/me")
    public JwtUser me(HttpServletRequest request) {
        return jwtTokenService.parse(request);
    }

    /**
     * JWT 登出
     */
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        jwtTokenService.revoke(request);
        return Map.of("ok", true);
    }
}
