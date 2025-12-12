package com.zichen.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUser {
    private Long id;
    private String username;
    private String passwordHash;
    private String nickname;
    private Integer status; // 1启用 0禁用
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

