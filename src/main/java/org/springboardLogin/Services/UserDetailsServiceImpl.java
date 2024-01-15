package org.springboardLogin.Services;



import org.springboardLogin.Entities.AppUser;
import org.springboardLogin.Entities.UserDetailsEntity;
import org.springboardLogin.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> userOptional = userRepository.findByUsername(username);

        AppUser user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));

        // Instead of storing the password directly, retrieve it securely (e.g., using BCryptPasswordEncoder)
        String encodedPassword = user.getPassword();

        // Example: Create authorities based on user roles
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        // Create UserDetails with username, encoded password, and authorities
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username, encodedPassword, authorities);

        return userDetails;
    }
}
