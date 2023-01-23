package com.meetnow.meetnowbackend.api.user.controller;

import com.meetnow.meetnowbackend.api.user.dto.NewUserDto;
import com.meetnow.meetnowbackend.api.user.service.ApiUserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final ApiUserService apiUserService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody NewUserDto.Request requestDto){

        Map<String, String> resultMap = apiUserService.save(requestDto);
        return ResponseEntity.ok(resultMap);
    }
}















