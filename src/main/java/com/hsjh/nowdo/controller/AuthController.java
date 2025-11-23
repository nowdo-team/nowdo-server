package com.hsjh.nowdo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.hsjh.nowdo.dto.auth.LoginRequest;
import com.hsjh.nowdo.dto.user.UserResponse;
import com.hsjh.nowdo.service.AuthService;
import com.hsjh.nowdo.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService){
        this.authService = authService;
        this.userService = userService;
    }

    //로그인 UserId 반환
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        UserResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    //마이페이지 조회 (경로 /me/추가)
    @GetMapping("/me/{userId}")
    public ResponseEntity<UserResponse> getMyInfo(@RequestParam Long userId) {
        UserResponse response = userService.getMyInfo(userId);
        return ResponseEntity.ok(response);
    }
    
}
