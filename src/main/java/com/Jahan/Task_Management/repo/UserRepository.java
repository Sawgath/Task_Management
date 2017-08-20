package com.Jahan.Task_Management.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Jahan.Task_Management.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	
	List<User> findByuserName(String userName);
}
