package com.Jahan.Task_Management.repo;
import org.springframework.data.repository.CrudRepository;
import com.Jahan.Task_Management.model.Project;
/*
 * ProjectRepository for adding or overriding existing function for Project entity.
 */
import com.Jahan.Task_Management.model.Role;
import com.Jahan.Task_Management.model.User;

public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	Project findByprojectId(long userId);

}
