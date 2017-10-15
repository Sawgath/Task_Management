package com.Jahan.Task_Management.repo;
import org.springframework.data.repository.CrudRepository;
import com.Jahan.Task_Management.model.Task;
/*
 * TaskRepository for adding or overriding existing function for task entity.
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

}
