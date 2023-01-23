package com.meetnow.meetnowbackend.api.room.controller;


import com.meetnow.meetnowbackend.api.room.dto.InvitationCodeDto;
import com.meetnow.meetnowbackend.api.room.dto.NewRoomDto;
import com.meetnow.meetnowbackend.api.room.dto.RoomListDto;
import com.meetnow.meetnowbackend.api.room.service.ApiRoomService;
import com.meetnow.meetnowbackend.domain.room.RoomService;
import com.meetnow.meetnowbackend.domain.user.User;
import com.meetnow.meetnowbackend.global.config.auth.LoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final ApiRoomService apiRoomService;
    private final RoomService roomService;
    /**
     *  어떤 요청의 경우는 응답DTO만 필요하거나, DTO 생성 자체가 필요없을 수도 있다.
     *  DELETE 요청의 경우 url에 달린 식별자를 @PathVariable 로 받고 삭제시킨 다음
     *  아무것도 반환하지 않을 수도 있음.
     */
    @ApiOperation(value = "방 생성")
    @PostMapping("/rooms")
    public ResponseEntity<NewRoomDto.Response> createRoom(@RequestBody NewRoomDto.Request requestDto
                                                            , @LoginUser User user
                                                          ){
        NewRoomDto.Response result = apiRoomService.save(requestDto, user);
        return ResponseEntity.ok(result);
    }

    // 사용자가 속한 방의 리스트 조회, 사용자 정보를 받고, 사용자가 속한 방(방이름, 초대코드)를 응답.
    @ApiOperation(value = "사용자가 속한 방 이름과 초대코드 모두 조회")
    @GetMapping("/rooms")
    public Map<String, List<RoomListDto>> roomList(@LoginUser User user){
        return apiRoomService.findAllByUser(user);
    }

    // 초대코드 받은 사용자가 입장함
    @ApiOperation(value = "초대코드 받은 사용자 입장")
    @PostMapping("/rooms/invitation")
    public ResponseEntity inviteUser(@RequestBody InvitationCodeDto invitationCodeDto,
                                     @LoginUser User user){

        roomService.invite(invitationCodeDto.getInvitationCode(), user);
        return ResponseEntity.noContent().build();
    }

}






















