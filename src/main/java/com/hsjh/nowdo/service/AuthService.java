package com.hsjh.nowdo.service;

import org.springframework.stereotype.Service;

import com.hsjh.nowdo.common.exception.NotFoundException;
import com.hsjh.nowdo.common.exception.UnauthorizedException;
import com.hsjh.nowdo.domain.user.User;
import com.hsjh.nowdo.dto.auth.LoginRequest;
import com.hsjh.nowdo.dto.user.UserResponse;
import com.hsjh.nowdo.repository.UserRepository;


// 11/23일 추가 작업, AuthService의 추가 작성 필요에 따라 작성.
@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //로그인 기능 (클라이언트)
    public UserResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new NotFoundException("존재하지 않는 이메일입니다."));

        if(!user.getPassword().equals(request.getPassword())) {
            throw new UnauthorizedException("잘못된 비밀번호입니다.");
        }

        return UserResponse.from(user);
    }


}
