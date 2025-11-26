package com.hsjh.nowdo.controller;

import com.hsjh.nowdo.dto.user.UserRegisterRequest;
import com.hsjh.nowdo.dto.user.UserResponse;
<<<<<<< HEAD
import com.hsjh.nowdo.dto.user.UpdateProfileRequest;
import com.hsjh.nowdo.service.UserService;

=======
import com.hsjh.nowdo.common.exception.UnauthorizedException;
import com.hsjh.nowdo.dto.user.UpdateProfileRequest;
import com.hsjh.nowdo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
>>>>>>> Feature/Exception_And_UserUpdate
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }


    //마이페이지 조회 기능 (11/20 추가)
    @PostMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(
<<<<<<< HEAD
        @AuthenticationPrincipal Long userId){
=======
        @AuthenticationPrincipal HttpServletRequest request){

            HttpSession session = request.getSession(false);

            if(session == null || session.getAttribute("userId") == null){
                throw new UnauthorizedException("로그인이 필요합니다.");
            }

            Long userId = (Long) session.getAttribute("userId");
>>>>>>> Feature/Exception_And_UserUpdate

            UserResponse response = userService.getMyInfo(userId);
            return ResponseEntity.ok(response);
        }
    
    //프로필 수정 (11/20 추가)
    @PutMapping("/me")
<<<<<<< HEAD
    public ResponseEntity<UserResponse> updateProfile (@AuthenticationPrincipal Long userId,
         @Valid @RequestBody UpdateProfileRequest request) {
        
            UserResponse response = userService.updateProfile(userId, request);
=======
    public ResponseEntity<UserResponse> updateProfile (@AuthenticationPrincipal HttpServletRequest request,
         @Valid @RequestBody UpdateProfileRequest updateRequest) {
            HttpSession session = request.getSession(false);

            if(session == null || session.getAttribute("userId")== null){
                throw new UnauthorizedException("로그인이 필요합니다.");
            }

            Long userId = (Long) session.getAttribute("userId");
        
            UserResponse response = userService.updateProfile(userId, updateRequest);
>>>>>>> Feature/Exception_And_UserUpdate
            return ResponseEntity.ok(response);
    
    }


}
