package org.springboardLogin.Repositories;

import org.springboardLogin.Entities.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on AppUser entities in MongoDB.
 */
public interface UserRepository extends MongoRepository<AppUser, String> {

    /**
     * Find a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return Optional containing the user if found, empty otherwise.
     */
    Optional<AppUser> findByUsername(String username);
}
