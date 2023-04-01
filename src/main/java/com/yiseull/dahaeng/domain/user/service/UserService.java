package com.yiseull.dahaeng.domain.user.service;

import com.yiseull.dahaeng.domain.user.dto.UserRequest;
import com.yiseull.dahaeng.domain.user.dto.UserResponse;

public interface UserService {

    void signUp(UserRequest.SignUp request);

    UserResponse.Profile login(UserRequest.Login request);

    void signOut(int userId);
}