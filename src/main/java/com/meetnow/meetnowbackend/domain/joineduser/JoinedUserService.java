package com.meetnow.meetnowbackend.domain.joineduser;

import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class JoinedUserService {

    private final JoinedUserRepository joinedUserRepository;

    @Transactional
    public JoinedUser save(JoinedUser joinedUser){
        return joinedUserRepository.save(joinedUser);
    }

    public boolean hasUserAndRoom(User user, Room room) {
        return joinedUserRepository.findByUserAndRoom(user, room).isPresent();
    }
}




















