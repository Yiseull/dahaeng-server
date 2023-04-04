package com.yiseull.dahaeng.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 회원
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "1000", "회원을 찾을 수 없습니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "1001", "중복된 닉네임입니다."),
    BAD_PASSWORD(HttpStatus.BAD_REQUEST, "1002", "비밀번호가 일치하지 않습니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "1003", "등록된 이메일을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
