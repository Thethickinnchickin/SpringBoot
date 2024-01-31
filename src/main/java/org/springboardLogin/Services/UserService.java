package org.springboardLogin.Services;

import org.springboardLogin.Entities.AppUser;
import org.springboardLogin.Entities.UserRole;
import org.springboardLogin.Repositories.UserRepository;
import org.springboardLogin.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Register a new user by encoding the password, assigning a default role, and saving to the repository.
     *
     * @param user The user to be registered.
     * @return The registered user.
     */
    public AppUser registerUser(AppUser user) {
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
        return user;
    }

    /**
     * Find a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return Optional containing the user if found, empty otherwise.
     */
    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get a list of all users in the system.
     *
     * @return List of all users.
     */
    public List<AppUser> getAllUsers() {
        // Fetch all users from the database and convert to a List
        return new ArrayList<>(userRepository.findAll());
    }

    /**
     * Get a user by their unique identifier (id).
     *
     * @param id The unique identifier of the user.
     * @return Optional containing the user if found, empty otherwise.
     */
    public Optional<AppUser> findByUserId(String id) {
        return userRepository.findById(id);
    }

    /**
     * Authenticate a user during the login process.
     *
     * @param user The user attempting to log in.
     * @return Optional containing the authenticated user if successful, empty otherwise.
     */
    public Optional<AppUser> userLogin(AppUser user) {
        Optional<AppUser> foundUserOpt = userRepository.findByUsername(user.getUsername());

        if (foundUserOpt.isPresent()) {
            AppUser foundUser = foundUserOpt.get();

            // Check if the entered password matches the stored password
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                // Passwords match, return the user
                return Optional.of(foundUser);
            } else {
                // Passwords do not match
                return Optional.empty();
            }
        } else {
            // User not found
            return Optional.empty();
        }
    }
}
