package com.meetnow.meetnowbackend.domain.room;

import com.meetnow.meetnowbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {



    @Query("SELECT r.roomName FROM Room r JOIN r.timeTables t JOIN t.user u WHERE u = :user")
    List<String> findAllRoomNameByUser(@Param("user") User user);
    List<Room> findAllByUser(User user);
    Room findByInvitationCode(String invitationCode);
}
