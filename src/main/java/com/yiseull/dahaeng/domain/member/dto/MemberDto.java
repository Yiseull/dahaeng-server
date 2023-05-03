package com.yiseull.dahaeng.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberInfo {
        private int userId;
        private int noteId;
    }
}
