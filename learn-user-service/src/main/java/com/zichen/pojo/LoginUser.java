package com.zichen.pojo;

import lombok.Data;

import java.io.Serializable;


public class LoginUser implements Serializable {

    private Long id;

    private String username;

    public LoginUser() {
    }

    public LoginUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
