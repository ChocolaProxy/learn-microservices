package com.zichen.pojo;


import lombok.Data;

@Data
public class JwtLoginReq {
    private String username;
    private String password;
}
