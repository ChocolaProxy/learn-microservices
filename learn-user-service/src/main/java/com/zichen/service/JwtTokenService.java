package com.zichen.service;


import com.zichen.pojo.JwtUser;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenService {

    String generate(JwtUser user);

    JwtUser parse(HttpServletRequest request);

    void revoke(HttpServletRequest request);
}
