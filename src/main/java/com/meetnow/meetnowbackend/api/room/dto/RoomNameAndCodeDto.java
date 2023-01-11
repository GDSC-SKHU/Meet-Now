package com.meetnow.meetnowbackend.api.room.dto;


import com.meetnow.meetnowbackend.domain.room.Room;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ApiModel(value = "메인페이지용 응답객체", description = "방 이름, 초대코드를 포함")
public class RoomNameAndCodeDto {

    @ApiModelProperty(value = "방 이름", required = true, example = "주동석방")
    private String roomName;

    @ApiModelProperty(value = "초대코드는 랜덤한 4자리 정수", required = true, example = "4123")
    private String invitationCode;

    public static List<RoomNameAndCodeDto> ofList(List<Room> rooms){
        return rooms.stream()
                .map(room -> RoomNameAndCodeDto.of(room))
                .collect(Collectors.toList());
    }

    public static RoomNameAndCodeDto of(Room room) {
        return RoomNameAndCodeDto.builder()
                .roomName(room.getRoomName())
                .invitationCode(room.getInvitationCode())
                .build();
    }
}

















