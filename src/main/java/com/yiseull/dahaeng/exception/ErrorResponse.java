package com.yiseull.dahaeng.exception;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ErrorResponse {

    private final String code;
    private final String message;

}
