package com.meetnow.meetnowbackend.domain.timetable;

import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TimeTableService {

    private final TimeTableRepository timeTableRepository;

    public TimeTable findByUserAndRoom(User user, Room room) {
        return timeTableRepository.findByUserAndRoom(user, room);
    }

    @Transactional
    public TimeTable save(TimeTable timeTable) {

        return timeTableRepository.save(timeTable);
    }
}
