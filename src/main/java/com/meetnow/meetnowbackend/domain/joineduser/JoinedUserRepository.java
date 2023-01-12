package com.meetnow.meetnowbackend.domain.joineduser;

import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JoinedUserRepository extends JpaRepository<JoinedUser, Long> {

    Optional<JoinedUser> findByUserAndRoom(User user, Room room);
}
