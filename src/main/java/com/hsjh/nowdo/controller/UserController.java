package com.hsjh.nowdo.controller;

import com.hsjh.nowdo.dto.user.UserRegisterRequest;
import com.hsjh.nowdo.dto.user.UserResponse;
import com.hsjh.nowdo.common.exception.UnauthorizedException;
import com.hsjh.nowdo.dto.user.UpdateProfileRequest;
import com.hsjh.nowdo.service.UserService;
import com.hsjh.nowdo.dto.auth.LoginRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    protected Long getUserIdFromSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("userId") == null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return (Long) session.getAttribute("userId");
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
        @RequestBody LoginRequest request, HttpServletRequest httpServletRequest) 
    {
    UserResponse response =
            userService.login(request.getEmail(), request.getPassword());

    HttpSession session = httpServletRequest.getSession(true);
    session.setAttribute("userId", response.getId());

    return ResponseEntity.ok(response);
    }


    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 완료");
    }
    


    //마이페이지 조회 기능 (11/20 추가)
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(HttpServletRequest request){

            HttpSession session = request.getSession(false);

            if(session == null || session.getAttribute("userId") == null){
                throw new UnauthorizedException("로그인이 필요합니다.");
            }

            Long userId = (Long) session.getAttribute("userId");
            UserResponse response = userService.getMyInfo(userId);

            return ResponseEntity.ok(response);
        }
    
    //프로필 수정 (11/20 추가)
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateProfile (HttpServletRequest request,
         @Valid @RequestBody UpdateProfileRequest updateRequest) {
            HttpSession session = request.getSession(false);

            if(session == null || session.getAttribute("userId")== null){
                throw new UnauthorizedException("로그인이 필요합니다.");
            }

            Long userId = (Long) session.getAttribute("userId");
        
            UserResponse response = userService.updateProfile(userId, updateRequest);
            return ResponseEntity.ok(response);
    
    }


}
