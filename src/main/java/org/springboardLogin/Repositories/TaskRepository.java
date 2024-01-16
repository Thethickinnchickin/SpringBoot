package org.springboardLogin.Repositories;

import org.springboardLogin.Entities.Task;
import org.springboardLogin.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

    /**
     * Find tasks associated with a specific user.
     * @param user The user to retrieve tasks for.
     * @return List of tasks associated with the user.
     */
    List<Task> findByUser(AppUser user);
}
