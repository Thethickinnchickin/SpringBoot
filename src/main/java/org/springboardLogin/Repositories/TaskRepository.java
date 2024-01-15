package org.springboardLogin.Repositories;

import org.springboardLogin.Entities.Task;
import org.springboardLogin.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(AppUser user);
}
