package com.yiseull.dahaeng.domain.user.service;

import com.yiseull.dahaeng.domain.user.User;
import com.yiseull.dahaeng.domain.user.dto.UserRequest;
import com.yiseull.dahaeng.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

    @Nested
    @DisplayName("회원가입 테스트")
    class signUp {

        @Test
        @DisplayName("회원가입 성공")
        void signUp_success() {

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

        @DisplayName("중복 이메일 예외")
        @Test
        void signUp_fail() {

            // given
            UserRequest.SignUp request = UserRequest.SignUp.builder()
                    .email("test123@naver.com")
                    .password("test123")
                    .nickname("테스트다옹")
                    .userColor(0)
                    .build();

            given(userRepository.existsByEmail(any())).willReturn(true);

            // then
            assertThatThrownBy(() -> userService.signUp(request)).hasMessage("중복된 이메일입니다.");
        }
    }
}