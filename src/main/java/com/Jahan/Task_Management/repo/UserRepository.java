package com.Jahan.Task_Management.repo;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.Jahan.Task_Management.model.User;

/*
 * User Repository for adding or overriding existing function for User entity. 
 */
public interface UserRepository extends CrudRepository<User, Long>{

	List<User> findByuserName(String userName);
	User findByEmail(String email);
}
