package com.meetnow.meetnowbackend.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUserId(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

























