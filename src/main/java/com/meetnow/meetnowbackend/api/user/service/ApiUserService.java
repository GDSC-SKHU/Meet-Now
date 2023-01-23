package com.meetnow.meetnowbackend.api.user.service;

import com.meetnow.meetnowbackend.domain.jwt.service.TokenProvider;
import com.meetnow.meetnowbackend.api.user.dto.NewUserDto;
import com.meetnow.meetnowbackend.domain.user.User;
import com.meetnow.meetnowbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiUserService {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Transactional
    public Map<String, String> save(NewUserDto.Request requestDto) {
        // 저장할 user
        User user = User.builder()
                .username(requestDto.getUsername())
                .build();

        /** UserService */
        // DB에 저장된 후에 다시 조회해온 User
        User savedUser = userService.save(user);

        // 토큰 생성 후 Map에 등록하고 반환. Map은 반환 Dto 역할을 한다.
        String token = tokenProvider.createToken(savedUser.getUsername());
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("accessToken", token);

        return resultMap;
    }
}














