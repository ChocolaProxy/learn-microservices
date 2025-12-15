package com.zichen.pojo;


import lombok.Data;

@Data
public class JwtUser {
    private Long id;
    private String username;
    private String nickname;
}
