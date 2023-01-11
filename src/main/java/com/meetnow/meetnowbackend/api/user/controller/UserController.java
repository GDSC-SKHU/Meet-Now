package com.meetnow.meetnowbackend.api.user.controller;

import com.meetnow.meetnowbackend.api.user.dto.NewUserDto;
import com.meetnow.meetnowbackend.domain.jwt.service.TokenProvider;
import com.meetnow.meetnowbackend.domain.user.User;
import com.meetnow.meetnowbackend.domain.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody NewUserDto.Request requestDto){

        // 저장할 user
        User user = User.builder()
                .username(requestDto.getUsername())
                .build();

        // DB에 저장된 후에 다시 조회해온 User
        User savedUser = userService.save(user);
        String token = tokenProvider.createToken(savedUser.getUsername());

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("accessToken", token);
        return ResponseEntity.ok(resultMap);
    }
}















