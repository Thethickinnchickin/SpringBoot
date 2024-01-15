package org.springboardLogin.Repositories;

import org.springboardLogin.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    /**
     * Find a user by their username.
     * @param username The username of the user to retrieve.
     * @return Optional containing the user if found, empty otherwise.
     */
    Optional<AppUser> findByUsername(String username);
}
