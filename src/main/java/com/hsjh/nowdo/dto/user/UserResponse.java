package com.hsjh.nowdo.dto.user;

import com.hsjh.nowdo.domain.user.User;
import com.hsjh.nowdo.domain.user.UserStatus;

public class UserResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final UserStatus status;
    private final String profileImg;

    public UserResponse(Long id, String email, String nickname,
                        UserStatus status, String profileImg) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.status = status;
        this.profileImg = profileImg;
    }

    public static UserResponse from(User user) {
    return new UserResponse(
        user.getId(),
        user.getEmail(),
        user.getNickname(),
        user.getStatus(),
        user.getProfileImg()
    );
}


    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
    public UserStatus getStatus() { return status; }
    public String getProfileImg() { return profileImg; }

    
}

