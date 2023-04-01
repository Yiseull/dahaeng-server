package com.yiseull.dahaeng.domain.user.dto;

import com.yiseull.dahaeng.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Getter
    @NoArgsConstructor
    public static class SignUp {
        private String email;
        private String password;
        private String nickname;

        public User toEntity(SignUp request) {
            return User.builder()
                    .email(this.email)
                    .password(this.password)
                    .nickname(this.nickname)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Login {
        private String email;
        private String password;
    }

}
