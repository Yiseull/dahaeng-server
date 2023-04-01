package com.yiseull.dahaeng.domain.user.controller;

import com.yiseull.dahaeng.domain.user.dto.UserRequest;
import com.yiseull.dahaeng.domain.user.dto.UserResponse;
import com.yiseull.dahaeng.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity signUp(@RequestBody UserRequest.SignUp request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse.Profile> login(@RequestBody UserRequest.Login request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity signOut(@PathVariable int userId) {
        userService.signOut(userId);
        return ResponseEntity.ok().build();
    }

}
