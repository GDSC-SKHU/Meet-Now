package com.meetnow.meetnowbackend.domain.timetable;

import com.meetnow.meetnowbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
    TimeTable findByUser(User user);
}
