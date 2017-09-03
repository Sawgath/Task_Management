package com.Jahan.Task_Management.repo;
import org.springframework.data.repository.CrudRepository;
import com.Jahan.Task_Management.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
