package com.meetnow.meetnowbackend.domain.room;

import com.meetnow.meetnowbackend.domain.user.User;
import com.meetnow.meetnowbackend.global.error.exception.BusinessException;
import com.meetnow.meetnowbackend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> findAllByUser(User user){
        return roomRepository.findAllByUser(user);
    }



    @Transactional
    public Room findByInvitationCode(String invitationCode){
        return roomRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_INVITATION_CODE));
    }

    public boolean hasInvitationCode(String invitationCode) {
        return roomRepository.findByInvitationCode(invitationCode).isPresent();
    }

}
















