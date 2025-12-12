package com.zichen.pojo;

import java.io.Serializable;

public class LoginUser implements Serializable {

    private Long id;
    private String username;

    public LoginUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
