package com.hsjh.nowdo.dto.user;

import jakarta.validation.constraints.NotBlank;

public class VerifyPasswordRequest {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public String getPassword() {
        return password;
    }
}
