package com.yiseull.dahaeng.domain.user.dto;

import com.yiseull.dahaeng.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUp {
        private String email;
        private String password;
        private String nickname;
        private int userColor;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        private String email;
        private String password;

    }

}
