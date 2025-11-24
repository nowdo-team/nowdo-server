package com.hsjh.nowdo.dto.auth;

import com.hsjh.nowdo.domain.user.User;

public class AuthUserResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String token;

    public AuthUserResponse(Long id, String email, String nickname, String token) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.token = token;
    }

    public static AuthUserResponse from(User user, String token) {
        return new AuthUserResponse(
            user.getId(),
            user.getEmail(),
            user.getNickname(),
            token
        );
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
    public String getToken() { return token; }
}
