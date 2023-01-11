package com.meetnow.meetnowbackend.domain.room;

import com.meetnow.meetnowbackend.domain.timetable.TimeTable;
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
public class Room{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name", length = 30, nullable = false)
    private String roomName;

    @Column(name = "appointment_Hour", length = 3, nullable = false)
    private Short appointmentHour;

    @Column(name = "invitation_code", length = 4, nullable = false)
    private String invitationCode;

    // StartDate와 endDate는 해당 방에서 잡을 수 있는 약속시간의 범위
    @Column(name = "start_date", length = 30, nullable = false)
    private String startDate;

    @Column(name = "end_date", length = 30, nullable = false)
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "room", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<TimeTable> timeTables;

    @Builder
    public Room(String roomName, Short appointmentHour, String invitationCode, String startDate, String endDate, User user) {
        this.roomName = roomName;
        this.appointmentHour = appointmentHour;
        this.invitationCode = invitationCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }
}













