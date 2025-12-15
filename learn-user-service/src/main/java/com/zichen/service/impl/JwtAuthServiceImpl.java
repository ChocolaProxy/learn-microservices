package com.zichen.service.impl;

import com.zichen.mapper.SysUserJwtMapper;
import com.zichen.pojo.JwtLoginReq;
import com.zichen.pojo.JwtUser;
import com.zichen.pojo.SysUserEntity;
import com.zichen.service.JwtAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthServiceImpl implements JwtAuthService {

    private final SysUserJwtMapper sysUserJwtMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtUser login(JwtLoginReq req) {
        SysUserEntity user = sysUserJwtMapper.findByUsername(req.getUsername());
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        if (user.getStatus() != 1) {
            throw new RuntimeException("用户已被禁用");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 只组装 JwtUser（JWT 用）
        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(user.getId());
        jwtUser.setUsername(user.getUsername());
        jwtUser.setNickname(user.getNickname());

        return jwtUser;
    }
}
