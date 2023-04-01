package com.yiseull.dahaeng.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Profile {

        private int userId;
        private String email;
        private String nickname;
        private int userColor;
    }
}
