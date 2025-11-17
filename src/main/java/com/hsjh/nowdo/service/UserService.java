package com.hsjh.nowdo.service;

import com.hsjh.nowdo.domain.user.User;
import com.hsjh.nowdo.domain.user.UserStatus;
import com.hsjh.nowdo.dto.user.UserRegisterRequest;
import com.hsjh.nowdo.dto.user.UserResponse;
import com.hsjh.nowdo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public UserResponse register(UserRegisterRequest request) {

        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 엔티티 생성
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());  // 나중에 반드시 암호화 필요
        user.setNickname(request.getNickname());
        user.setStatus(UserStatus.ACTIVE);
        user.setProfileImg(null);

        // DB 저장
        User saved = userRepository.save(user);

        // DTO로 변환해서 반환
        return UserResponse.from(saved);
    }
}
