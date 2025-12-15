package com.zichen.service;

import com.zichen.pojo.JwtLoginReq;
import com.zichen.pojo.JwtUser;

public interface JwtAuthService {

    /**
     * 用户名密码登录校验
     */
    JwtUser login(JwtLoginReq req);

}
