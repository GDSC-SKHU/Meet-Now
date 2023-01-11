package com.meetnow.meetnowbackend.domain.appointmentdate;

import com.meetnow.meetnowbackend.domain.timetable.TimeTable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity

public class AppointmentDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 날짜(월/일), 시작시간, 끝나는 시간이 필요함
    @Column(name = "date", nullable = false)
    private String date;

    // appoStart와 appoEnd는 방에 소속된 사용자가 등록한 시간(Hour)
    @Column(name = "appo_start", nullable = false)
    private String appoStart;

    @Column(name = "appo_end", nullable = false)
    private String appoEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timetable_id", nullable = false)
    private TimeTable timeTable;

    @Builder
    public AppointmentDate(String date, String appoStart, String appoEnd, TimeTable timeTable) {
        this.date = date;
        this.appoStart = appoStart;
        this.appoEnd = appoEnd;
        this.timeTable = timeTable;
    }
}
