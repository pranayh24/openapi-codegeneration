package org.openapi.codegen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapi.codegen.document.User;
import org.openapi.codegen.exception.ResourceNotFoundException;
import org.openapi.codegen.exception.ValidationException;
import org.openapi.codegen.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public User createUser(String username, String email, String password, String firstName, String lastName) {
        log.info("Creating new user with username: {}", username);

        if (userRepository.existsByUsername(username)) {
            throw new ValidationException("Username already exists: " + username);
        }

        if (userRepository.existsByEmail(email)) {
            throw new ValidationException("Email already exists: " + email);
        }

        User user = new User(username, email, passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCreatedBy("system");
        user.setUpdatedBy("system");

        User savedUser = userRepository.save(user);
        log.info("Successfully created user with id: {}", savedUser.getId());

        return savedUser;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User" + id));
    }

    public User updateUser(String userId, String firstName, String lastName, String email) {
        log.info("Updating user with id: {}", userId);

        User user = findById(userId);

        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new ValidationException("Email already exists: " + email);
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUpdatedBy(userId);

        User updatedUser = userRepository.save(user);
        log.info("Successfully updated user with id: {}", userId);

        return updatedUser;
    }

    public void changePassword(String userId, String oldPassword, String newPassword) {
        log.info("Changing password for user with id: {}", userId);

        User user = findById(userId);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ValidationException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedBy(userId);

        userRepository.save(user);
        log.info("Successfully changed user password for user : {}", userId);
    }

    public void incrementGenerationCount(String userId) {
        User user = findById(userId);
        user.setGenerationCount(user.getGenerationCount() + 1);
        userRepository.save(user);
    }

    public void addToTotalCost(String userId, Double cost) {
        User user = findById(userId);
        user.setTotalCost(user.getTotalCost() + cost);
        userRepository.save(user);
    }

    public void deactivateUser(String userId) {
        log.info("Deactivating user with id: {}", userId);
        User user = findById(userId);
        user.setActive(false);
        user.setUpdatedBy("system");

        userRepository.save(user);
        log.info("Successfully deactivated user with id: {}", userId);
    }

    public void activateUser(String userId) {
        log.info("Activating user with id: {}", userId);
        User user = findById(userId);
        user.setActive(true);
        user.setUpdatedBy("system");

        userRepository.save(user);
        log.info("Successfully activated user with id: {}", userId);
    }

    public List<User> findAllActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    public boolean validateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.isActive() && passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
