package com.yiseull.dahaeng.domain.user.service;

import com.yiseull.dahaeng.domain.user.User;
import com.yiseull.dahaeng.domain.user.dto.UserRequest;
import com.yiseull.dahaeng.domain.user.dto.UserResponse;
import com.yiseull.dahaeng.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

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

            given(userRepository.existsByEmail(anyString())).willReturn(false);
            given(userRepository.save(any(User.class))).willReturn(User.builder()
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
            verify(userRepository, times(1)).existsByEmail(anyString());
            verify(userRepository, times(1)).save(any(User.class));
            verify(passwordEncoder, times(1)).encode(anyString());
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

            given(userRepository.existsByEmail(anyString())).willReturn(true);

            // when
            assertThatThrownBy(() -> userService.signUp(request)).hasMessage("중복된 이메일입니다.");
        }
    }

    @Nested
    @DisplayName("로그인 테스트")
    class login {

        private User user;

        @BeforeEach
        void setUp() {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user = User.builder()
                    .userId(1)
                    .email("test123@naver.com")
                    .password(encoder.encode("test123"))
                    .nickname("테스트다옹")
                    .userColor(3)
                    .build();
        }

        @Test
        @DisplayName("로그인 성공")
        void login_success() {

            // given
            UserRequest.Login request = UserRequest.Login.builder()
                    .email("test123@naver.com")
                    .password("test123")
                    .build();

            given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(user));

            // when
            UserResponse.Profile loginUser = userService.login(request);

            // then
            assertThat(user.getUserId()).isEqualTo(loginUser.getUserId());
            assertThat(user.getEmail()).isEqualTo(loginUser.getEmail());
            assertThat(user.getNickname()).isEqualTo(loginUser.getNickname());
            assertThat(user.getUserColor()).isEqualTo(loginUser.getUserColor());

            // verify
            verify(userRepository, times(1)).findByEmail(anyString());
            verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        }

        @DisplayName("등록된 이메일이 없는 경우")
        @Test
        void login_email_not_found() {

            // given
            UserRequest.Login request = UserRequest.Login.builder()
                    .email("test123@naver.com")
                    .password("test123")
                    .build();

            given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(null));

            // when
            assertThatThrownBy(() -> userService.login(request)).hasMessage("등록된 이메일을 찾을 수 없습니다.");
        }

        @DisplayName("비밀번호가 일치하지 않는 경우")
        @Test
        void login_bad_password() {

            // given
            UserRequest.Login request = UserRequest.Login.builder()
                    .email("test123@naver.com")
                    .password("test456")
                    .build();

            given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(user));

            // when
            assertThatThrownBy(() -> userService.login(request)).hasMessage("비밀번호가 일치하지 않습니다.");
        }
    }
}