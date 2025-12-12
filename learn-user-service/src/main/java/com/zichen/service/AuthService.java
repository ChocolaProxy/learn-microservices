package com.zichen.service;

import com.zichen.config.OnlineUserStore;
import com.zichen.mapper.SysUserMapper;
import com.zichen.pojo.LoginReq;
import com.zichen.pojo.LoginUser;
import com.zichen.pojo.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class AuthService {

    public static final String SESSION_USER_KEY = "LOGIN_USER";

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final OnlineUserStore onlineUserStore;

    public LoginUser login(LoginReq req, HttpSession session) {
        if (req == null || req.getUsername() == null || req.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "参数错误");
        }

        SysUser user = sysUserMapper.selectByUsername(req.getUsername());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "账号已被禁用");
        }

        boolean passOk = passwordEncoder.matches(req.getPassword(), user.getPasswordHash());
        if (!passOk) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
        }

        LoginUser loginUser = new LoginUser(user.getId(), user.getUsername());
        session.setAttribute(SESSION_USER_KEY, loginUser);
        onlineUserStore.put(session.getId(), loginUser);

        return loginUser;
    }

    public LoginUser currentUser(HttpSession session) {
        Object obj = session.getAttribute(SESSION_USER_KEY);
        if (obj == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "未登录");
        }
        return (LoginUser) obj;
    }

    public void logout(HttpSession session) {
        onlineUserStore.remove(session.getId());
        session.invalidate();
    }
}
