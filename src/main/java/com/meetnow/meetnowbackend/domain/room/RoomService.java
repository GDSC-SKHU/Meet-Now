package com.meetnow.meetnowbackend.domain.room;

import com.meetnow.meetnowbackend.domain.user.User;
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

//    public List<Room> findAllByTimeTable(TimeTable timeTable) {
//        return roomRepository.findAllByTimeTable(timeTable);
//    }

    public List<String> findAllRoomNameByUser(User user) {
        return roomRepository.findAllRoomNameByUser(user);
    }

    public List<Room> findAllByUser(User user){
        return roomRepository.findAllByUser(user);
    }

    @Transactional
    public Room findByInvitationCode(String invitationCode){
        return roomRepository.findByInvitationCode(invitationCode);
    }
}
















