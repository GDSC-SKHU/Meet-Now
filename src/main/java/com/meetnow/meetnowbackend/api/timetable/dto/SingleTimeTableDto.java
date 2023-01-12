package com.meetnow.meetnowbackend.api.timetable.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SingleTimeTableDto {
    private String roomName;
    private String startDate;
    private String invitationCode;
    private String username;
    private List<TimeTableListDto.AppointmentDateDto> timeList;
}
