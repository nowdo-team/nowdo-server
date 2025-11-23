package com.hsjh.nowdo.dto.user;

import jakarta.validation.constraints.NotBlank;

public class UpdateProfileRequest {

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    // 프로필 이미지는 선택값이어서 validation 미구현
    private String profileImg;

    public String getNickname(){ return nickname; }
    public String getProfileImg() { return profileImg; }
}
