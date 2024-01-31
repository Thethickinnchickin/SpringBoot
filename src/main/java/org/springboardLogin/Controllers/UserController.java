package org.springboardLogin.Controllers;

import org.springboardLogin.DTOs.LoginResponseDTO;
import org.springboardLogin.DTOs.ResponseDTO;
import org.springboardLogin.DTOs.UserDTO;
import org.springboardLogin.Entities.AppUser;
import org.springboardLogin.Security.JwtTokenProvider;
import org.springboardLogin.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AppUser user) {
        // Log information about registering a new user
        System.out.println("Registering User");
        userService.registerUser(user);
        ResponseDTO responseDTO = new ResponseDTO("User registered successfully");
        return ResponseEntity.ok(responseDTO);
    }

    // Authenticate and generate JWT token for login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AppUser user) {
        try {
            Optional<AppUser> newUser = userService.userLogin(user);

            if (newUser.isPresent()) {
                // User authentication successful, generate JWT token
                AppUser foundUser = newUser.get();
                String token = jwtTokenProvider.generateToken(foundUser);
                LoginResponseDTO loginResponse = new LoginResponseDTO(token);
                return ResponseEntity.ok(loginResponse);
            } else {
                // Unable to log in
                return ResponseEntity.ok("Unable To Login");
            }
        } catch (AuthenticationException e) {
            // Handle authentication failure
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        // Log information about retrieving all users
        System.out.println("Retrieving all users");
        List<AppUser> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get a user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        Optional<AppUser> optionalUser = userService.findByUserId(id);

        if (optionalUser.isPresent()) {
            // Convert AppUser to UserDTO
            AppUser user = optionalUser.get();
            UserDTO userDTO = new UserDTO(user.getId(), user.getUsername());
            return ResponseEntity.ok(userDTO);
        } else {
            // User with the given ID not found
            return ResponseEntity.notFound().build();
        }
    }
}
