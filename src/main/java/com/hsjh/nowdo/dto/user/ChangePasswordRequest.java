package com.hsjh.nowdo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    @NotBlank
    private String currentPassward;

    @NotBlank
    @Size(min = 8, max = 20)
    private String newPassword;

    public String getCurrentPassword(){
        return currentPassward;
    }

    public String getNewPassword(){
        return newPassword;
    }
    
}
