package com.meetnow.meetnowbackend.domain.room;

import com.meetnow.meetnowbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
//    @Query("SELECT r.roomName FROM Room r JOIN r.timeTables t JOIN t.user u WHERE u = :user")
//    List<String> findAllRoomNameByUser(@Param("user") User user);

    @Query("SELECT r FROM JoinedUser j JOIN j.room r JOIN j.user u WHERE u = :user")
    List<Room> findAllByUser(@Param("user") User user);

    Optional<Room> findByInvitationCode(String invitationCode);


    @Query("UPDATE Room  r SET r.roomName = :roomName WHERE r.roomName = :roomName")
    @Modifying
    void findBRN(@Param("roomName") String roomName);

    void deleteByRoomName(String roomName);
}















