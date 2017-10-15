package com.Jahan.Task_Management.repo;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.Jahan.Task_Management.model.Role;
import com.Jahan.Task_Management.model.User;

/*
 * User Repository for adding or overriding existing function for User entity. 
 */
public interface UserRepository extends CrudRepository<User, Long>{

	List<User> findByuserName(String userName);
	List<User> findByemail(String email);
	User findByRole(Role role);
	User findByEmail(String email);
	User findByuserId(long userId);
	User findUserByResetToken(String resetToken);
}
