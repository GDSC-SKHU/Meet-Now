package com.meetnow.meetnowbackend.api.timetable.dto;

import com.meetnow.meetnowbackend.domain.appointmentdate.AppointmentDate;
import com.meetnow.meetnowbackend.domain.timetable.TimeTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TimeTableListDto {
//    private String roomName;
//    private Short appointmentHour;
//    private String invitationCode;
//    private String startDate;
//    private String endDate;
//
//    List<TimeTableDto> singleTimeTableDto;

    @Builder
    @Getter
    @ApiModel(value = "사용자별 타임테이블을 담은 객체", description = "사용자별 타임테이블")
    public static class TimeTableDto {
        @ApiModelProperty(value = "시간표를 등록한 사용자명", required = true, example = "주동석")
        private String username;

        @ApiModelProperty(value = "해당 사용자가 가능한 약속시간", required = true, example = "")
        private List<AppointmentDateDto> appointmentDateDto;

        // 정적 팩토리 메소드
        public static TimeTableDto of(TimeTable timeTable){
            return TimeTableDto.builder()
                    .username(timeTable.getUser().getUsername())
                    .appointmentDateDto(AppointmentDateDto.ofList(timeTable.getAppointmentDates()))
                    .build();
        }

        public static List<TimeTableDto> ofList(List<TimeTable> timeTables){
            return timeTables.stream()
                    .map(tt -> TimeTableDto.of(tt))
                    .collect(Collectors.toList());
        }

    }

    @Builder
    @Getter
    @ApiModel(value = "가능한 약속일을 담은 객체", description = "이 객체는 하나의 사용자가 여러 개를 가질 수 있음(1월 1일, 1월 2일, 1월 3일 모두 가능할 경우")
    public static class AppointmentDateDto {

        @ApiModelProperty(value = "날짜", required = true, example = "01-23(목)")
        private String date;
        @ApiModelProperty(value = "약속시간. 24시간을 기준으로 함.", required = true, example = "20")
        private String time;

        public static AppointmentDateDto of(AppointmentDate appointmentDate){
            return AppointmentDateDto.builder()
                    .date(appointmentDate.getDate())
                    .time(appointmentDate.getTime())
                    .build();
        }

        public static List<AppointmentDateDto> ofList(List<AppointmentDate> appointmentDates){
            return appointmentDates.stream()
                    .map((appo) -> AppointmentDateDto.of(appo))
                    .collect(Collectors.toList());
        }
    }

}