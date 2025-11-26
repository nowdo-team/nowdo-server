package com.hsjh.nowdo.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.RestController;

import com.hsjh.nowdo.dto.auth.LoginRequest;
=======
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.hsjh.nowdo.common.exception.UnauthorizedException;
import com.hsjh.nowdo.dto.auth.LoginRequest;
import com.hsjh.nowdo.dto.user.UpdateProfileRequest;
>>>>>>> Feature/Exception_And_UserUpdate
import com.hsjh.nowdo.dto.user.UserRegisterRequest;
import com.hsjh.nowdo.dto.user.UserResponse;
import com.hsjh.nowdo.service.AuthService;
import com.hsjh.nowdo.service.UserService;

<<<<<<< HEAD
=======

>>>>>>> Feature/Exception_And_UserUpdate
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestParam;
=======
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> Feature/Exception_And_UserUpdate



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
<<<<<<< HEAD
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request){
=======
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request){
>>>>>>> Feature/Exception_And_UserUpdate
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    //로그인 UserId 반환
    @PostMapping("/login")
<<<<<<< HEAD
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        UserResponse response = authService.login(request);
=======
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        
        UserResponse response = authService.login(request);

        //로그인 유지용 세션 생성
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("userId", response.getId());
        
>>>>>>> Feature/Exception_And_UserUpdate
        return ResponseEntity.ok(response);
    }
    
    //마이페이지 조회 (경로 /auth/추가 11/24)
<<<<<<< HEAD
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getMyInfo(@RequestParam Long userId) {
        UserResponse response = userService.getMyInfo(userId);
        return ResponseEntity.ok(response);
    }
=======
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);

        Long userId = (Long) session.getAttribute("userId");

        UserResponse response = userService.getMyInfo(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
        HttpServletRequest request, @RequestBody UpdateProfileRequest updateRequest
    ){
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        Long userId = (Long) session.getAttribute("userId");

        UserResponse response = userService.updateProfile(userId, updateRequest);
        return ResponseEntity.ok(response);

    }
>>>>>>> Feature/Exception_And_UserUpdate
    
}
