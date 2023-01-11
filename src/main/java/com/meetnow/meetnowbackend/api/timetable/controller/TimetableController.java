package com.meetnow.meetnowbackend.api.timetable.controller;

import com.meetnow.meetnowbackend.api.timetable.dto.NewAppoDto;
import com.meetnow.meetnowbackend.api.timetable.dto.TimeTableListDto;
import com.meetnow.meetnowbackend.domain.appointmentdate.AppointmentDate;
import com.meetnow.meetnowbackend.domain.appointmentdate.AppointmentDateService;
import com.meetnow.meetnowbackend.domain.jwt.service.TokenProvider;
import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.room.RoomService;
import com.meetnow.meetnowbackend.domain.timetable.TimeTable;
import com.meetnow.meetnowbackend.domain.timetable.TimeTableService;
import com.meetnow.meetnowbackend.domain.user.User;
import com.meetnow.meetnowbackend.domain.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TimetableController {

    private final TimeTableService timeTableService;
    private final UserService userService;
    private final RoomService roomService;
    private final AppointmentDateService appointmentDateService;

    private final TokenProvider tokenProvider;

    /**
     * 해당 방의 시간표 모두 조회
     * - 요청 - 방의 초대코드 보내기
     * - 응답 - 해당 방의 전체 사용자의 시간표 모두 보내주기
     * - #### 방에 처음 입장할 때, 새로고침 버튼을 누를 때 사용됨
     */
    @ApiOperation(value = "해당 방의 시간표 모두 조회")
    @GetMapping("/timetables/rooms/{invitationCode}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<TimeTableListDto.TimeTableDto>> allTimeTable(@PathVariable String invitationCode) {


        Room byInvitationCode = roomService.findByInvitationCode(invitationCode); // 초대코드로 room 찾기
        List<TimeTableListDto.TimeTableDto> result = TimeTableListDto.TimeTableDto.ofList(byInvitationCode.getTimeTables()); // Lazy Loading
        return ResponseEntity.ok(result);
    }

    /**
     * 요청 - 어떤 요일, 몇시부터 몇시까지 가능한지 (의 리스트)를 0번에서 발급받은 토큰과 함께 보냄
     *     // 지금 토큰 방식이 아니니까 사용자를 구분 다른 요소(username, id(PK))
     *     초대코드로 방을, 토큰이나 다른 값으로 사용자를 식별 가능
     *  1. 내가 누군지 (String username) // 완료
     *  2. 어떤 방에 타임테이블을 만들건지 (invitationCode) // 완료
     *  3. 그 타임테이블에 어떤 AppointmentDate들이 들어갈지
     * 응답 - 반환타입 void
     */

    @ApiOperation(value = "사용자별 시간표 등록")
    @PostMapping("/timetables/rooms/{invitationCode}") // 시간표 등록
    public void createTimeTable(
                                @RequestBody List<NewAppoDto> requestDto
            , HttpServletRequest request
            , @PathVariable String invitationCode){

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        String username = tokenProvider.getUsername(accessToken);
        User user = userService.findByUsername(username);

        TimeTable tt = timeTableService.findByUser(user);
        if(tt != null){
            throw new IllegalArgumentException("타임테이블 중복 생성 불가. 수정 기능 미완성");
        }
        Room byInvitationCode = roomService.findByInvitationCode(invitationCode); // 초대코드로 room 찾기

        TimeTable timeTable = TimeTable.builder() // timetable 객체 생성
                .user(user)
                .room(byInvitationCode)
                .build();

        TimeTable savedTable = timeTableService.save(timeTable);// 저장
//4번 완료
//        5. AppointmentDate 객체 생성에 필요한 데이터를 @RequestBody 뭐시기DTO.Request로 받아옴
//        6. AppointmentDate.builder().date().appoStart().appoEnd()
//                .timeTable(4번에서 생성된 TimeTable).build() 로 객체 생성
//        7. AppointmentService.save(6번에서 만든 객체)
//        8. 끝 ! 저장만 하고 반환하지 않는다.

        List<AppointmentDate> appoDates = requestDto.stream()
                .map(appoDto -> appoDto.toEntity(savedTable))
                .collect(Collectors.toList());

//        for (AppointmentDate appoDate : appoDates) {
//            appointmentDateService.save(appoDate);
//        }
        appoDates.stream()
                        .forEach(appo -> appointmentDateService.save(appo));
    }
}