package com.yiseull.dahaeng.domain.user.service;

import com.yiseull.dahaeng.domain.user.User;
import com.yiseull.dahaeng.domain.user.dto.UserRequest;
import com.yiseull.dahaeng.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    @InjectMocks
    private UserServiceImpl userService;

    @DisplayName("회원 가입 성공")
    @Test
    void signUp() {

        // given
        UserRequest.SignUp request = UserRequest.SignUp.builder()
                .email("test123@naver.com")
                .password("test123")
                .nickname("테스트다옹")
                .userColor(0)
                .build();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(request.getPassword());

        given(userRepository.existsByEmail(any())).willReturn(false);
        given(userRepository.save(any())).willReturn(User.builder()
                .email(request.getEmail())
                .password(encryptedPassword)
                .nickname(request.getNickname())
                .userColor(request.getUserColor())
                .build());

        // when
        User user = userService.signUp(request);

        // then
        assertThat(request.getEmail()).isEqualTo(user.getEmail());
        assertThat(passwordEncoder.matches(request.getPassword(), user.getPassword())).isTrue();
        assertThat(request.getNickname()).isEqualTo(user.getNickname());

        // verify
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(any(String.class));
    }

}