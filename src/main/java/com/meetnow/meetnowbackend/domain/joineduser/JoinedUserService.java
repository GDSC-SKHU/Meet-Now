package com.meetnow.meetnowbackend.domain.joineduser;

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
}
