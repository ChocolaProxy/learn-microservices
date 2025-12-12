package com.zichen.config;


import com.zichen.pojo.LoginUser;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OnlineUserStore {

    // sessionId -> LoginUser
    private final Map<String, LoginUser> online = new ConcurrentHashMap<>();

    public void put(String sessionId, LoginUser user) {
        online.put(sessionId, user);
    }

    public void remove(String sessionId) {
        online.remove(sessionId);
    }

    public List<LoginUser> list() {
        return new ArrayList<>(online.values());
    }

    public int count() {
        return online.size();
    }
}
