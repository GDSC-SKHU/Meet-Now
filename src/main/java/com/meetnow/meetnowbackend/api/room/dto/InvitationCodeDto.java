package com.meetnow.meetnowbackend.api.room.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "초대코드만을 보유함", description = "초대코드를 body에 json으로 보내주세요")
@NoArgsConstructor
@Getter
public class InvitationCodeDto {
    private String invitationCode;
}
