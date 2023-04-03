package com.yiseull.dahaeng.domain.user.controller;

import com.yiseull.dahaeng.domain.user.dto.UserRequest;
import com.yiseull.dahaeng.domain.user.dto.UserResponse;
import com.yiseull.dahaeng.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailValidate(@RequestParam String email) {
        log.info("email: {}", email);
        return ResponseEntity.ok(userService.CheckEmailDuplicate(email));
    }

    @PostMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNicknameValidate(@RequestParam String nickname) {
        log.info("nickname: {}", nickname);
        return ResponseEntity.ok(userService.CheckNicknameDuplicate(nickname));
    }

    @PostMapping("/{userId}/nickname")
    public ResponseEntity<UserResponse.Profile> updateNickname(@PathVariable int userId, @RequestParam String nickname) {
        return ResponseEntity.ok(userService.updateNickname(userId, nickname));
    }

    @PostMapping("/{userId}/password")
    public ResponseEntity updateNickname(@PathVariable int userId, @RequestBody Map<String, Object> request) {
        userService.updatePassword(userId, (String) request.get("password"));
        return ResponseEntity.ok().build();
    }

    /**
     * 이메일 인증
     * @param request 요청 이메일
     * @return 인증 코드
     */
    @PostMapping("/email")
    public ResponseEntity<String> authenticateEmail(@RequestBody Map<String, Object> request) throws Exception {
        String authCode = userService.sendMail((String) request.get("email"), 0);
        return ResponseEntity.ok(authCode);
    }

    /**
     * 비밀번호 찾기
     * @param request 요청 이메일
     * @return
     */
    @PostMapping("/password")
    public ResponseEntity findPassword(@RequestBody Map<String, Object> request) throws Exception {
        userService.sendMail((String) request.get("email"), 1);
        return ResponseEntity.ok().build();
    }

}
