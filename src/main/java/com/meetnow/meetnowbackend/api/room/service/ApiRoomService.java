package com.meetnow.meetnowbackend.api.room.service;

import com.meetnow.meetnowbackend.api.room.dto.NewRoomDto;
import com.meetnow.meetnowbackend.api.room.dto.RoomListDto;
import com.meetnow.meetnowbackend.domain.joineduser.JoinedUser;
import com.meetnow.meetnowbackend.domain.joineduser.JoinedUserService;
import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.room.RoomService;
import com.meetnow.meetnowbackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ApiRoomService {

    private final RoomService roomService;
    private final JoinedUserService joinedUserService;

    @Transactional
    public NewRoomDto.Response save(NewRoomDto.Request requestDto, User user){
        Random random = new Random();
        String invitationCode = String.valueOf(random.nextInt(99999 - 10000 + 1) + 10000);

        while (roomService.hasInvitationCode(invitationCode)){
            invitationCode = String.valueOf(random.nextInt(99999 - 10000 + 1) + 10000);
        }

        Room room = Room.builder()
                .roomName(requestDto.getRoomName())
                .appointmentHour(requestDto.getAppointmentHour())
                .invitationCode(invitationCode)
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .build();

        /** RoomService */
        Room savedRoom = roomService.save(room);

        JoinedUser joinedUser = JoinedUser.builder()
                .user(user)
                .room(room)
                .build();

        // 어떤 방에 어떤 사용자가 들어갔는지 저장
        /** JoinedUserService */
        joinedUserService.save(joinedUser);

        return new NewRoomDto.Response(savedRoom.getRoomName(), savedRoom.getInvitationCode());
    }

    public Map<String, List<RoomListDto>> findAllByUser(User user) {
        /** RoomService */
        List<Room> rooms = roomService.findAllByUser(user);

        List<RoomListDto> roomDtoList = RoomListDto.ofList(rooms);

        Map<String, List<RoomListDto>> resultMap = new HashMap<>();
        resultMap.put("rooms", roomDtoList);
        return resultMap;
    }
}
























