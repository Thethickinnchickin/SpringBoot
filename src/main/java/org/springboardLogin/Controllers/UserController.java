package org.springboardLogin.Controllers;

import org.springboardLogin.DTOs.LoginResponseDTO;
import org.springboardLogin.DTOs.ResponseDTO;
import org.springboardLogin.DTOs.UserDTO;
import org.springboardLogin.Entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springboardLogin.Security.JwtTokenProvider;
import org.springboardLogin.Services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AppUser user) {
        userService.registerUser(user);
        ResponseDTO responseDTO = new ResponseDTO("User registered successfully");
        return ResponseEntity.ok(responseDTO);
    }

    // Authenticate and generate JWT token for login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AppUser user) {
        try {
            user.getPassword();
            user.getUsername();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtTokenProvider.generateToken(userDetails);
            LoginResponseDTO loginResponse = new LoginResponseDTO(token);
            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException e) {
            // Handle authentication failure
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
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
