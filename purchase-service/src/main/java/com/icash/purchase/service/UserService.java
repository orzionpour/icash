package com.icash.purchase.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.icash.purchase.entity.User;
import com.icash.purchase.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Creates a new user and saves it to the repository.
     * 
     * @return the created User entity
     */
    @Transactional
    public User createUser() {
        User user = new User();
        return userRepository.save(user);
    }

    /**
     * Checks if a user exists by their ID.
     * 
     * @param userId the UUID of the user
     * @return true if the user exists, false otherwise
     */
    @Transactional
    public boolean userExists(UUID userId) {
        return userRepository.existsById(userId);
    }
}
