package com.meetnow.meetnowbackend.api.room.controller;


import com.meetnow.meetnowbackend.api.room.dto.NewRoomDto;
import com.meetnow.meetnowbackend.api.room.dto.RoomNameAndCodeDto;
import com.meetnow.meetnowbackend.domain.jwt.service.TokenProvider;
import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.room.RoomService;
import com.meetnow.meetnowbackend.domain.timetable.TimeTableService;
import com.meetnow.meetnowbackend.domain.user.User;
import com.meetnow.meetnowbackend.domain.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final UserService userService;
    private final TimeTableService timeTableService;
    private final TokenProvider tokenProvider;

    /**
     *  어떤 요청의 경우는 응답DTO만 필요하거나, DTO 생성 자체가 필요없을 수도 있다.
     *  DELETE 요청의 경우 url에 달린 식별자를 @PathVariable 로 받고 삭제시킨 다음
     *  아무것도 반환하지 않을 수도 있음.
     */
    @ApiOperation(value = "방 생성")
    @PostMapping("/rooms")
    public ResponseEntity<NewRoomDto.Response> createRoom(@RequestBody NewRoomDto.Request requestDto
                                                            , HttpServletRequest request
                                                          ){
        // 1. 뭘 받아올지 -> requestDto, request

        // 2. 받아온걸로 뭘 할지
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        String username = tokenProvider.getUsername(accessToken);
        User user = userService.findByUsername(username);

        Random random = new Random();
        String invitationCode = String.valueOf(random.nextInt(9999 - 1000 + 1) + 1000);

        Room room = Room.builder()
                .roomName(requestDto.getRoomName())
                .appointmentHour(requestDto.getAppointmentHour())
                .invitationCode(invitationCode)
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .user(user)
                .build();

        Room savedRoom = roomService.save(room);
        // 3. 무엇을 반환(응답)할지
        NewRoomDto.Response result = new NewRoomDto.Response(savedRoom.getRoomName(), savedRoom.getInvitationCode());
        return ResponseEntity.ok(result);
    }

    // 사용자가 속한 방의 리스트 조회, 사용자 정보를 받고, 사용자가 속한 방(방이름, 초대코드)를 응답.
    @ApiOperation(value = "사용자가 속한 방 모두 조회")
    @GetMapping("/rooms")
    public Map<String, List<RoomNameAndCodeDto>> roomList(HttpServletRequest request){

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        String username = tokenProvider.getUsername(accessToken);
        User user = userService.findByUsername(username);

        //Room room = roomService.findAllByTimeTable(timeTable);
//        List<String> roomNames = roomService.findAllRoomNameByUser(user);
        List<Room> rooms = roomService.findAllByUser(user);

        List<RoomNameAndCodeDto> roomDtoList = RoomNameAndCodeDto.ofList(rooms);

        Map<String, List<RoomNameAndCodeDto>> resultMap = new HashMap<>();
        resultMap.put("rooms", roomDtoList);

        return resultMap;
    }

}






















