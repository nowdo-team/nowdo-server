package com.hsjh.nowdo.service;

import com.hsjh.nowdo.common.exception.NotFoundException;
import com.hsjh.nowdo.common.exception.UnauthorizedException;
import com.hsjh.nowdo.domain.user.User;
import com.hsjh.nowdo.domain.user.UserStatus;
import com.hsjh.nowdo.dto.user.UpdateProfileRequest;
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

    // 로그인 기능
    public UserResponse login(String email, String password) {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("이메일이 존재하지 않습니다."));

    if (!user.getPassword().equals(password)) {
        throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
    }

    return UserResponse.from(user);
    }


    // 마이페이지 조회
    public UserResponse getMyInfo (Long userId){

        if(userId == null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(()->
        new NotFoundException("존재하지 않는 사용자입니다."));

        return UserResponse.from(user);
    }
    // 프로필 수정
    public UserResponse updateProfile(Long userId, UpdateProfileRequest updateRequest){

        if(userId == null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(()-> 
        new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setNickname(updateRequest.getNickname());
        user.setProfileImg(updateRequest.getProfileImg());

        User updated = userRepository.save(user);

        return UserResponse.from(updated);
    }

    // 프로필 이미지 변경
    public void updateProfileImage(Long userId, String fileName){
        if(userId == null){
            throw new UnauthorizedException("사용자를 찾을 수 없습니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(()
        -> new NotFoundException("사용자 없음"));

        user.setProfileImg(fileName);
        userRepository.save(user);
    }


}
