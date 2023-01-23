package com.meetnow.meetnowbackend.api.timetable.service;

import com.meetnow.meetnowbackend.api.timetable.dto.NewAppoDto;
import com.meetnow.meetnowbackend.api.timetable.dto.SingleTimeTableDto;
import com.meetnow.meetnowbackend.api.timetable.dto.TimeTableListDto;
import com.meetnow.meetnowbackend.domain.appointmentdate.AppointmentDate;
import com.meetnow.meetnowbackend.domain.appointmentdate.AppointmentDateService;
import com.meetnow.meetnowbackend.domain.joineduser.JoinedUserService;
import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.room.RoomService;
import com.meetnow.meetnowbackend.domain.timetable.TimeTable;
import com.meetnow.meetnowbackend.domain.timetable.TimeTableService;
import com.meetnow.meetnowbackend.domain.user.User;
import com.meetnow.meetnowbackend.global.error.exception.BusinessException;
import com.meetnow.meetnowbackend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ApiTimeTableService {
    private final RoomService roomService;
    private final JoinedUserService joinedUserService;
    private final TimeTableService timeTableService;
    private final AppointmentDateService appointmentDateService;

    public SingleTimeTableDto findByUserAndInvitationCode(String invitationCode, User user) {

        /** RoomService */
        // 초대코드로 방 조회
        Room room = roomService.findByInvitationCode(invitationCode);

        /** JoinedUserService */
        // 1. 해당 유저가 해당 invatationCode에 join되어있는지 확인
        if (! joinedUserService.hasUserAndRoom(user, room)){
            throw new BusinessException(ErrorCode.USER_NOT_IN_ROOM);
        }

        /** TimeTableService */
        // 2.  join 되어있으므로, 해당 방에서 해당 유저가 가진 타임테이블 반환
        TimeTable timeTable = timeTableService.findByUserAndRoom(user, room);

        // 타임테이블 객체가 가진 AppoDate의 리스트를 Dto의 리스트로 변환.
        List<TimeTableListDto.AppointmentDateDto> appoListDto = TimeTableListDto.AppointmentDateDto.ofList(timeTable.getAppointmentDates());

        // SingleTimeTableDto 반환
        return SingleTimeTableDto.builder()
                .roomName(room.getRoomName())
                .startDate(room.getStartDate())
                .invitationCode(room.getInvitationCode())
                .username(user.getUsername())
                .timeList(appoListDto)
                .build();
    }

    public TimeTableListDto findAllByInvitationCode(String invitationCode) {
        /** RoomService */
        // 1. 초대코드로 room 찾기
        Room room = roomService.findByInvitationCode(invitationCode);

        // room이 가진 TimeTable List를 모두 Dto로 변환해서 RETURN
        List<TimeTableListDto.TimeTableDto> result = TimeTableListDto.TimeTableDto.ofList(room.getTimeTables()); // Lazy Loading
        return TimeTableListDto.builder()
                .timeTableList(result)
                .invitationCode(invitationCode)
                .roomName(room.getRoomName())
                .startDate(room.getStartDate())
                .build();
    }

    @Transactional
    public void saveOrUpdate(List<NewAppoDto> requestDto, String invitationCode, User user) {
        // 1. 초대코드로 Room 객체를 찾는다.
        Room room = roomService.findByInvitationCode(invitationCode);

        TimeTable timeTable = timeTableService.findByUserAndRoom(user, room);

        if(timeTable == null){ // 이미 만든 타임테이블이 없다면 새로 등록한다.
            timeTable = TimeTable.builder() // timetable 객체 생성
                    .user(user)
                    .room(room)
                    .build();

            timeTable = timeTableService.save(timeTable);// 저장
            // 한 번도 변경하지 않은 effectively final 변수도 가능하다.
            // 이렇게 불변한 읽기전용 final 변수는 안전하다. 배열을 사용해 우회적으로 접근하는 방식은 위험하다. https://www.baeldung.com/java-lambda-effectively-final-local-variables
            final TimeTable finalTimeTable = timeTable;

            List<AppointmentDate> appoDates = requestDto.stream()
                    .map(appoDto -> appoDto.toEntity(finalTimeTable))
                    .collect(Collectors.toList());

            appoDates.stream()
                    .forEach(appo -> appointmentDateService.save(appo));
            return;
        } // 이미 만든 타임테이블이 있다면 그 안의 내용을 모두 삭제한 후 새로 등록한다.

        // 수정을 위해 해당 타임테이블에 속한 appointmentDate를 모두 제거
        appointmentDateService.deleteByTimeTable(timeTable);

        final TimeTable finalTimeTable = timeTable;
        List<AppointmentDate> appoDates = requestDto.stream()
                .map(appoDto -> appoDto.toEntity(finalTimeTable))
                .collect(Collectors.toList());

        appoDates.stream()
                .forEach(appo -> appointmentDateService.save(appo));
    }
}





















