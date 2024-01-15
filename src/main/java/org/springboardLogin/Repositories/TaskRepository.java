package org.springboardLogin.Repositories;

import org.springboardLogin.Entities.Task;
import org.springboardLogin.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find tasks associated with a specific user.
     * @param user The user to retrieve tasks for.
     * @return List of tasks associated with the user.
     */
    List<Task> findByUser(AppUser user);
}
