package com.zichen.config;

import com.zichen.mapper.SysUserMapper;
import com.zichen.pojo.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitConfig {

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initUsers() {
        return args -> {
            createIfNotExists("admin", "123456", "tt");
            createIfNotExists("tom", "111111", "Tom");
            createIfNotExists("jerry", "222222", "Jerry");
        };
    }

    private void createIfNotExists(String username, String rawPwd, String nickname) {
        SysUser exists = sysUserMapper.selectByUsername(username);
        if (exists != null) return;

        SysUser u = new SysUser();
        u.setUsername(username);
        u.setPasswordHash(passwordEncoder.encode(rawPwd));
        u.setNickname(nickname);
        u.setStatus(1);
        sysUserMapper.insert(u);

        System.out.println("[INIT] created user: " + username + " / " + rawPwd);
    }
}
