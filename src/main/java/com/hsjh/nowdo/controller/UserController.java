package com.hsjh.nowdo.controller;

import com.hsjh.nowdo.dto.user.EmailCheckResponse;
import com.hsjh.nowdo.dto.user.UserRegisterRequest;
import com.hsjh.nowdo.dto.user.UserResponse;
import com.hsjh.nowdo.common.exception.UnauthorizedException;
import com.hsjh.nowdo.common.util.FileStorageUtil;
import com.hsjh.nowdo.dto.user.ChangePasswordRequest;
import com.hsjh.nowdo.dto.user.UpdateProfileRequest;
import com.hsjh.nowdo.dto.user.VerifyPasswordRequest;
import com.hsjh.nowdo.service.UserService;
import com.hsjh.nowdo.dto.auth.LoginRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private static final String PROFILE_VERIFIED_SESSION_KEY = "profileVerified";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    protected HttpSession getAuthenticatedSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("userId") == null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return session;
    }

    protected Long getUserIdFromSession(HttpServletRequest request){
        HttpSession session = getAuthenticatedSession(request);
        return (Long) session.getAttribute("userId");
    }

    private void ensureProfileVerified(HttpSession session) {
        Object verified = session.getAttribute(PROFILE_VERIFIED_SESSION_KEY);
        if (!(verified instanceof Boolean) || !((Boolean) verified)) {
            throw new UnauthorizedException("프로필에 접근하기 전에 비밀번호 확인이 필요합니다.");
        }
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/check-email")
    public ResponseEntity<EmailCheckResponse> checkEmail(@RequestParam("email") String email) {
        boolean available = userService.isEmailAvailable(email);
        return ResponseEntity.ok(new EmailCheckResponse(available));
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

            HttpSession session = getAuthenticatedSession(request);
            ensureProfileVerified(session);

            Long userId = (Long) session.getAttribute("userId");
            UserResponse response = userService.getMyInfo(userId);

            return ResponseEntity.ok(response);
        }
    
    //프로필 수정 (11/20 추가)
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateProfile (HttpServletRequest request,
         @Valid @RequestBody UpdateProfileRequest updateRequest) {
            HttpSession session = getAuthenticatedSession(request);
            ensureProfileVerified(session);

            Long userId = (Long) session.getAttribute("userId");
        
            UserResponse response = userService.updateProfile(userId, updateRequest);
            return ResponseEntity.ok(response);
    
    }
    //이미지 수정 
    @PostMapping("/me/image-url")
    public ResponseEntity<String> uploadProfileImage(HttpServletRequest request,
        @RequestParam String imageURL
    ) throws IOException {
        HttpSession session = getAuthenticatedSession(request);
        ensureProfileVerified(session);
        Long userId = (Long) session.getAttribute("userId");

        String savedFileName = FileStorageUtil.saveProfileImage(imageURL);

        userService.updateProfileImage(userId, savedFileName); // DB 저장용
    
        return ResponseEntity.ok(savedFileName);
    
    }

    @PostMapping("/profile/image-url")
    public ResponseEntity<String> updateProfileImageByUrl(
    HttpServletRequest request, @RequestParam String imageUrl) {
    HttpSession session = getAuthenticatedSession(request);
    ensureProfileVerified(session);
    Long userId = (Long) session.getAttribute("userId");

    userService.updateProfileImage(userId, imageUrl);

    return ResponseEntity.ok(imageUrl);

    }

    //비밀번호 수정 (12/14 추가)
    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(
        HttpServletRequest request,
        @Valid @RequestBody ChangePasswordRequest req
    ) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        Long userId = (Long) session.getAttribute("userId");

        userService.changePassword(userId, req);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/me/verify-password")
    public ResponseEntity<Void> verifyPassword(
        HttpServletRequest request,
        @Valid @RequestBody VerifyPasswordRequest req
    ) {
        HttpSession session = getAuthenticatedSession(request);
        Long userId = (Long) session.getAttribute("userId");

        userService.verifyPassword(userId, req.getPassword());
        session.setAttribute(PROFILE_VERIFIED_SESSION_KEY, true);

        return ResponseEntity.ok().build();
    }

}
