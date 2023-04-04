package com.yiseull.dahaeng.domain.user.service;

import com.yiseull.dahaeng.domain.user.User;
import com.yiseull.dahaeng.domain.user.dto.UserRequest;
import com.yiseull.dahaeng.domain.user.dto.UserResponse;
import com.yiseull.dahaeng.domain.user.repository.UserRepository;
import com.yiseull.dahaeng.exception.ErrorCode;
import com.yiseull.dahaeng.exception.UserException;
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
        if (userRepository.existsByEmail(request.getEmail()))
            throw new UserException(ErrorCode.DUPLICATE_EMAIL);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        request.setUserColor((int)(Math.random() * 5));
        userRepository.save(request.toEntity(request));
    }

    @Override
    public UserResponse.Profile login(UserRequest.Login request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(ErrorCode.EMAIL_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException(ErrorCode.BAD_PASSWORD);
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
            throw new UserException(ErrorCode.DUPLICATE_NICKNAME);
        }
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
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
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        findUser.updatePassword(passwordEncoder.encode(password));
    }

    @Override
    public String sendMail(String email, int option) throws Exception {
        User findUser = userRepository.findByEmail(email).orElseThrow(() -> new UserException(ErrorCode.EMAIL_NOT_FOUND));
        String authCode = mailService.sendMail(email, option);

        if (option == 1) {  // 비밀번호 찾기 경우 임시 비밀번호 업데이트
            String encodedPassword = passwordEncoder.encode(authCode);
            updatePassword(findUser.getUserId(), encodedPassword);
        }

        return authCode;
    }

}
