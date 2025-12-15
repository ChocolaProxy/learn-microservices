package com.zichen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class JwtLoginResp {
    private String accessToken;
    private String tokenType;   // 固定 "Bearer"
    private JwtUser user;
}
