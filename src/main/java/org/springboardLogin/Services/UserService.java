package org.springboardLogin.Services;

import org.springboardLogin.Entities.AppUser;

import org.springboardLogin.Entities.UserRole;
import org.springboardLogin.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user by encoding the password, assigning a default role, and saving to the repository
    public void registerUser(AppUser user) {
        // Encode the user's password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Assign a default role to the user
        Set<UserRole> defaultRoles = new HashSet<>();
        defaultRoles.add(UserRole.USER);
        //defaultRoles.add(UserRole.ADMIN);

        // Set the user's roles
        user.setRoles(defaultRoles);

        // Save the user to the repository
        userRepository.save(user);
    }

    // Find a user by their username
    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Get a list of all users in the system
    public List<AppUser> getAllUsers() {
        // Fetch all users from the database and convert to a List
        return new ArrayList<>(userRepository.findAll());
    }



    // Get a user by their unique identifier (id)
    public Optional<AppUser> findByUserId(String id) {

        return userRepository.findById(id);
    }

}

