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
        private int userColor;

        public User toEntity(SignUp request) {
            return User.builder()
                    .email(this.email)
                    .password(this.password)
                    .nickname(this.nickname)
                    .userColor(this.userColor)
                    .build();
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setUserColor(int userColor) {
            this.userColor = userColor;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Login {
        private String email;
        private String password;

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
