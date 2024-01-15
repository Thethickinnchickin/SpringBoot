package org.springboardLogin.Services;

import org.springboardLogin.Entities.AppUser;
import org.springboardLogin.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(AppUser user) {
        // Encode the user's password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<AppUser> getAllUsers() {
        // Fetch all users from the database
        return userRepository.findAll();
    }

    public AppUser getUserFromId(Long id) {
        return userRepository.getById(id);

    }
}
