package com.meetnow.meetnowbackend.api.room.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NewRoomDto {

    @NoArgsConstructor
    @Getter
    @ApiModel(value = "방을 등록하는 요청 객체", description = "방 등록을 위한 정보 포함")
    public static class Request{
        @ApiModelProperty(value = "방 이름", required = true, example = "주동석방")
        private String roomName;
        @ApiModelProperty(value = "약속시간의 길이. 단위[Hour]", required = true, example = "4")
        private Short appointmentHour;

        // StartDate와 endDate는 해당 방에서 잡을 수 있는 약속시간의 범위
        @ApiModelProperty(value = "약속시간 범위 시작일", required = true, example = "01-11")
        private String startDate;
        @ApiModelProperty(value = "약속시간 범위 종료일", required = true, example = "01-15")
        private String endDate;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ApiModel(value = "방 등록 후의 응답 객체", description = "등록된 방 이름, 초대코드 포함")
    public static class Response{
        @ApiModelProperty(value = "방이름", required = true, example = "호지영방")
        private String roomName;
        @ApiModelProperty(value = "초대코드는 랜덤한 4자리 정수", required = true, example = "4123")
        private String invitationCode;

    }




}


