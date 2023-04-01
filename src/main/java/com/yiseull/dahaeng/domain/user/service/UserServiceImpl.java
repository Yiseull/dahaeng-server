package com.yiseull.dahaeng.domain.user.service;

import com.yiseull.dahaeng.domain.user.User;
import com.yiseull.dahaeng.domain.user.dto.UserRequest;
import com.yiseull.dahaeng.domain.user.dto.UserResponse;
import com.yiseull.dahaeng.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void signUp(UserRequest.SignUp request) {
        userRepository.save(request.toEntity(request));
    }

    @Override
    public UserResponse.Profile login(UserRequest.Login request) {
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new RuntimeException());//  Custom Exception 으로 변경하기

        return UserResponse.Profile.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .userColor(user.getUserColor())
                .build();
    }

    @Override
    public void signOut(int userId) {
        userRepository.deleteById(userId);
    }

}
