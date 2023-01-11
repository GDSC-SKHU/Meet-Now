package com.meetnow.meetnowbackend.domain.timetable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meetnow.meetnowbackend.domain.appointmentdate.AppointmentDate;
import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User는  timetable 을 모르게 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToMany(mappedBy = "timeTable", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<AppointmentDate> appointmentDates;

    @Builder
    public TimeTable(User user, Room room) {
        this.user = user;
        this.room = room;
    }
}




















