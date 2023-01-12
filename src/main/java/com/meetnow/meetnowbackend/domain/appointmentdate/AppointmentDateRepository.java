package com.meetnow.meetnowbackend.domain.appointmentdate;

import com.meetnow.meetnowbackend.domain.timetable.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentDateRepository extends JpaRepository<AppointmentDate, Long> {


    public void deleteByTimeTable(TimeTable timeTable);
}
