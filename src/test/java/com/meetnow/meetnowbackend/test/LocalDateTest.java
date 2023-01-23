package com.meetnow.meetnowbackend.test;

import com.meetnow.meetnowbackend.domain.room.Room;
import com.meetnow.meetnowbackend.domain.room.RoomRepository;
import com.meetnow.meetnowbackend.domain.room.RoomService;
import com.meetnow.meetnowbackend.domain.timetable.TimeTable;
import com.meetnow.meetnowbackend.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;


@SpringBootTest
@Rollback(value = false)
public class LocalDateTest {

    @Autowired
    RoomService roomService;
    @Autowired
    RoomRepository roomRepository;

//    @Autowired
//    EntityManager em;

    @Autowired
    EntityManagerFactory emf;

    @Test
    public void testLocalDate() throws Exception{
        Room room = Room.builder()
                .endDate("")
                .startDate("")
                .invitationCode("")
                .appointmentHour((short) 1)
                .roomName("안현수방")
                .build();

        User user = User.builder()
                .username("안현수")
                .build();

        TimeTable timeTable = TimeTable.builder()
                .user(user)
                .room(room)
                .build();

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(user);
        em.persist(room);
        em.persist(timeTable);

        tx.commit();
//
        em.clear();
        TimeTable emTimeTable = em.find(TimeTable.class, 1L);

        System.out.println("컨텍스트 없이 지연로딩 들어간다");
        em.clear();
        // 트랜잭션 없이 지연로딩
        System.out.println(emTimeTable.getUser().getUsername());

        em.close();






//        roomRepository.deleteByRoomName(saved.getRoomName());

//
//        System.out.println(roomService.findById(1L));
//        System.out.println(roomService.findById(1L));
//        System.out.println(roomService.findById(1L));




//        em.persist(room);
//        em.flush();
//        em.clear();
//
//        System.out.println(em.find(Room.class, 1L).getRoomName());
//        System.out.println(em.find(Room.class, 1L).getRoomName());
     }

}
















