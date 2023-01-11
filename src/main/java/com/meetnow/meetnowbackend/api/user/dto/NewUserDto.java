package com.meetnow.meetnowbackend.api.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NewUserDto {

    // 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ApiModel("사용자의 이름만을 받는 회원가입용 객체")
    public static class Request{

        @ApiModelProperty(value = "이름", required = true, example = "주동석")
        private String username;
    }

}
