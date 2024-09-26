package com.ssk.SpringSecurityWithDaoAndJwt.service;

import com.ssk.SpringSecurityWithDaoAndJwt.dao.UserRepository;
import com.ssk.SpringSecurityWithDaoAndJwt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Create a new user with password encoding
    public User createUser(User user) {
        // Encode the password before saving the user
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    // 2. Get a user by ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // 3. Get a user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 4. Update an existing user with password encoding (if password is being changed)
    public User updateUser(int id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    // Update fields
                    user.setUsername(updatedUser.getUsername());
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());

                    // Encode the password only if it has been updated
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
                        user.setPassword(encodedPassword);
                    }

                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    // 5. Delete a user by ID
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    // 6. Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



}
