package com.meetnow.meetnowbackend.domain.timetable;

import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
    TimeTable findByUser(User user);



    Optional<TimeTable> findByUserAndRoom(User user, Room room);
}
