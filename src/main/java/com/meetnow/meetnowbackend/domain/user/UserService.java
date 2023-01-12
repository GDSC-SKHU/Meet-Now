package com.meetnow.meetnowbackend.domain.user;

import com.meetnow.meetnowbackend.global.error.exception.BusinessException;
import com.meetnow.meetnowbackend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        try {
            return userRepository.save(user);
            // 회원명 중복 시.
        } catch (DataIntegrityViolationException e){
            throw new BusinessException(ErrorCode.DUPLICATED_USERNAME);
        }

    }

    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

























