package com.meetnow.meetnowbackend.api.timetable.controller;

import com.meetnow.meetnowbackend.api.timetable.dto.NewAppoDto;
import com.meetnow.meetnowbackend.api.timetable.dto.SingleTimeTableDto;
import com.meetnow.meetnowbackend.api.timetable.dto.TimeTableListDto;
import com.meetnow.meetnowbackend.api.timetable.service.ApiTimeTableService;
import com.meetnow.meetnowbackend.domain.user.User;
import com.meetnow.meetnowbackend.global.config.auth.LoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimetableController {

    private final ApiTimeTableService apiTimeTableService;

    @ApiOperation(value = "단건 조회")
    @Transactional(readOnly = true)
    @GetMapping("/timetables/room/{invitationCode}")
    public ResponseEntity<SingleTimeTableDto> getSingleTimeTable(@LoginUser User user, @PathVariable String invitationCode){

        SingleTimeTableDto resultDto = apiTimeTableService.findByUserAndInvitationCode(invitationCode, user);
        return ResponseEntity.ok(resultDto);
    }

    @ApiOperation(value = "해당 방의 시간표 모두 조회")
    @GetMapping("/timetables/rooms/{invitationCode}")
    @Transactional(readOnly = true)
    public ResponseEntity<TimeTableListDto> getAllTimeTable(@PathVariable String invitationCode) {

        TimeTableListDto resultDto = apiTimeTableService.findAllByInvitationCode(invitationCode);
        return ResponseEntity.ok(resultDto);
    }


    @ApiOperation(value = "사용자별 시간표 등록, 수정")
    // 해당 메소드는 시간표 등록과 수정을 모두 담당한다.
    @PutMapping("/timetables/rooms/{invitationCode}")
    public ResponseEntity updateTimeTable( @RequestBody List<NewAppoDto> requestDto
            , @LoginUser User user
            , @PathVariable String invitationCode){

        apiTimeTableService.saveOrUpdate(requestDto, invitationCode, user);
        return ResponseEntity.noContent().build();
    }
}




























