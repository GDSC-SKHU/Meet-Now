package com.meetnow.meetnowbackend.domain.room;

import com.meetnow.meetnowbackend.domain.joineduser.JoinedUser;
import com.meetnow.meetnowbackend.domain.joineduser.JoinedUserService;
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
    private final JoinedUserService joinedUserService;
    @Transactional
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> findAllByUser(User user){
        return roomRepository.findAllByUser(user);
    }

    @Transactional
    public void invite(String invitationCode, User user) {
        // 2.초대코드를 받아서 방을 찾아온다.
        Room room = findByInvitationCode(invitationCode);

        // 3. 이미 입장한 방인지 찾는다.
        if (joinedUserService.hasUserAndRoom(user, room))
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);

        // 4. user와 room이 준비되었으니, 매핑객체 JoinedUser 생성 후 저장
        JoinedUser joinedUser = JoinedUser.builder()
                .user(user)
                .room(room)
                .build();

        joinedUserService.save(joinedUser);
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
















