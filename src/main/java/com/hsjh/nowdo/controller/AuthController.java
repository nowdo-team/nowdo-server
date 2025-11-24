package com.hsjh.nowdo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.hsjh.nowdo.dto.auth.LoginRequest;
import com.hsjh.nowdo.dto.user.UserRegisterRequest;
import com.hsjh.nowdo.dto.user.UserResponse;
import com.hsjh.nowdo.service.AuthService;
import com.hsjh.nowdo.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService){
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request){
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    //로그인 UserId 반환
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        UserResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    //마이페이지 조회 (경로 /auth/추가 11/24)
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getMyInfo(@RequestParam Long userId) {
        UserResponse response = userService.getMyInfo(userId);
        return ResponseEntity.ok(response);
    }
    
}
