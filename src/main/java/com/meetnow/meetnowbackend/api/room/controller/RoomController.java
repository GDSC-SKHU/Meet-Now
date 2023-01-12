package com.meetnow.meetnowbackend.api.room.controller;


import com.meetnow.meetnowbackend.api.room.dto.InvitationCodeDto;
import com.meetnow.meetnowbackend.api.room.dto.NewRoomDto;
import com.meetnow.meetnowbackend.api.room.dto.RoomNameAndCodeDto;
import com.meetnow.meetnowbackend.domain.joineduser.JoinedUser;
import com.meetnow.meetnowbackend.domain.joineduser.JoinedUserRepository;
import com.meetnow.meetnowbackend.domain.joineduser.JoinedUserService;
import com.meetnow.meetnowbackend.domain.jwt.service.TokenProvider;
import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.room.RoomService;
import com.meetnow.meetnowbackend.domain.user.User;
import com.meetnow.meetnowbackend.domain.user.UserService;
import com.meetnow.meetnowbackend.global.error.exception.BusinessException;
import com.meetnow.meetnowbackend.global.error.exception.ErrorCode;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    private final TokenProvider tokenProvider;
    private final JoinedUserService joinedUserService;
    private final JoinedUserRepository joinedUserRepository;

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
                .build();

        Room savedRoom = roomService.save(room);

        JoinedUser joinedUser = JoinedUser.builder()
                .user(user)
                .room(room)
                .build();

        // 어떤 방에 어떤 사용자가 들어갔는지 저장
        joinedUserService.save(joinedUser);
        // 3. 무엇을 반환(응답)할지
        NewRoomDto.Response result = new NewRoomDto.Response(savedRoom.getRoomName(), savedRoom.getInvitationCode());
        return ResponseEntity.ok(result);
    }

    // 사용자가 속한 방의 리스트 조회, 사용자 정보를 받고, 사용자가 속한 방(방이름, 초대코드)를 응답.
    @ApiOperation(value = "사용자가 속한 방 이름과 초대코드 모두 조회")
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

    // 초대코드 받은 사용자가 입장함
    @ApiOperation(value = "초대코드 받은 사용자 입장")
    @PostMapping("/rooms/invitation")
    public ResponseEntity inviteUser(@RequestBody InvitationCodeDto invitationCodeDto, HttpServletRequest request){

        // 1. 누구를 초대해야 할지 정한다. 토큰값에서 이름 빼오고 유저 찾기.
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        String username = tokenProvider.getUsername(accessToken);
        User user = userService.findByUsername(username);

        // 2.초대코드를 받아서 방을 찾아온다.
        Room room = roomService.findByInvitationCode(invitationCodeDto.getInvitationCode());

        // 3. 이미 입장한 방인지 찾는다.
        if (joinedUserService.hasUserAndRoom(user, room))
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);

        // 4. user와 room이 준비되었으니, 매핑객체 JoinedUser 생성 후 저장
        JoinedUser joinedUser = JoinedUser.builder()
                .user(user)
                .room(room)
                .build();
        joinedUserService.save(joinedUser);

        return ResponseEntity.noContent().build();
    }

}






















