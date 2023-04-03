package com.yiseull.dahaeng.domain.user.service;

import com.yiseull.dahaeng.domain.user.User;
import com.yiseull.dahaeng.domain.user.dto.UserRequest;
import com.yiseull.dahaeng.domain.user.dto.UserResponse;
import com.yiseull.dahaeng.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public void signUp(UserRequest.SignUp request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        request.setUserColor((int)(Math.random() * 5));
        userRepository.save(request.toEntity(request));
    }

    @Override
    public UserResponse.Profile login(UserRequest.Login request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException()); //  Custom Exception 으로 변경하기

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException(); //  Custom Exception 으로 변경하기
        }

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

    @Override
    public boolean CheckEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean CheckNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public UserResponse.Profile updateNickname(int userId, String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException();   //  Custom Exception 으로 변경하기
        }
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());  //  Custom Exception 으로 변경하기
        findUser.updateNickname(nickname);

        return UserResponse.Profile.builder()
                .userId(userId)
                .email(findUser.getEmail())
                .nickname(nickname)
                .userColor(findUser.getUserColor())
                .build();
    }

    @Override
    public void updatePassword(int userId, String password) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());  //  Custom Exception 으로 변경하기
        findUser.updatePassword(passwordEncoder.encode(password));
    }

    @Override
    public String sendMail(String email, int option) throws Exception {
        String authCode = mailService.sendMail(email, option);

        if (option == 1) {  // 비밀번호 찾기 경우 임시 비밀번호 업데이트
            User findUser = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException());
            String encodedPassword = passwordEncoder.encode(authCode);
            updatePassword(findUser.getUserId(), encodedPassword);
        }

        return authCode;
    }

}
