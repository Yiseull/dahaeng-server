package com.yiseull.dahaeng.domain.user.controller;

import com.yiseull.dahaeng.domain.user.dto.UserRequest;
import com.yiseull.dahaeng.domain.user.dto.UserResponse;
import com.yiseull.dahaeng.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "User 👥", description = "회원 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임을 받아 회원가입")
    @PostMapping
    public ResponseEntity signUp(@RequestBody UserRequest.SignUp request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그인", description = "이메일, 비밀번호를 받아 로그인")
    @PostMapping("/login")
    public ResponseEntity<UserResponse.Profile> login(@RequestBody UserRequest.Login request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @Operation(summary = "회원탈퇴", description = "userId를 받아 해당 회원 삭제")
    @DeleteMapping("/{userId}")
    public ResponseEntity signOut(@PathVariable int userId) {
        userService.signOut(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이메일 중복 체크", description = "DB에 해당 이메일이 있는지 확인")
    @PostMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailValidate(@RequestParam String email) {
        log.info("email: {}", email);
        return ResponseEntity.ok(userService.CheckEmailDuplicate(email));
    }

    @Operation(summary = "닉네임 중복 체크", description = "DB에 해당 닉네임이 있는지 확인")
    @PostMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNicknameValidate(@RequestParam String nickname) {
        log.info("nickname: {}", nickname);
        return ResponseEntity.ok(userService.CheckNicknameDuplicate(nickname));
    }

    @Operation(summary = "닉네임 변경", description = "닉네임 변경")
    @PostMapping("/{userId}/nickname")
    public ResponseEntity<UserResponse.Profile> updateNickname(@PathVariable int userId, @RequestParam String nickname) {
        return ResponseEntity.ok(userService.updateNickname(userId, nickname));
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경")
    @PostMapping("/{userId}/password")
    public ResponseEntity updatePassword(@PathVariable int userId, @RequestBody Map<String, Object> request) {
        userService.updatePassword(userId, (String) request.get("password"));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이메일 인증", description = "요청 이메일에 인증번호 보내기")
    @PostMapping("/email")
    public ResponseEntity<String> authenticateEmail(@RequestBody Map<String, Object> request) throws Exception {
        String authCode = userService.sendMail((String) request.get("email"), 0);
        return ResponseEntity.ok(authCode);
    }

    @Operation(summary = "비밀번호 찾기", description = "요청 이메일에 임시 비밀번호 발급")
    @PostMapping("/password")
    public ResponseEntity findPassword(@RequestBody Map<String, Object> request) throws Exception {
        userService.sendMail((String) request.get("email"), 1);
        return ResponseEntity.ok().build();
    }

}
