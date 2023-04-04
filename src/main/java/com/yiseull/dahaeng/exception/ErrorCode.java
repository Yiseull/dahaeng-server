package com.yiseull.dahaeng.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 회원
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "1000", "회원이 존재하지 않습니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "1001", "중복된 닉네임입니다."),
    WRONG_PASSWORD(HttpStatus.FORBIDDEN, "1002", "비밀번호가 틀렸습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
