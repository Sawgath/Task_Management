package com.Jahan.Task_Management.helper;

import com.Jahan.Task_Management.model.User;
import com.Jahan.Task_Management.repo.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Jahan.Task_Management.helperModel.UserHelperModel;

@Component
public class LoginHelper {
	
	@Autowired
	UserRepository userRepository;
	
	//User's Name and password checking.
	public String CheckUser(UserHelperModel aUserHelper){
		
		List<User> UserList = userRepository.findByuserName(aUserHelper.userName);
		
		if(UserList.size()==1) 
		{
			for(User auser : UserList)
			{
				if(auser.getpassword().equals(aUserHelper.getpassword()))
				{
					//Matched password for user.
					return "Success";
				}
			}
		}
	
		return "Fail";
	}
	
	public long getUserId(UserHelperModel aUserHelper){
		
		List<User> UserList = userRepository.findByuserName(aUserHelper.userName);
		
		if(UserList.size()==1) 
		{
			for(User auser : UserList)
			{
				if(auser.getpassword().equals(aUserHelper.getpassword()))
				{
					//Matched password for user.
					return auser.getuserId();
				}
			}
		}
		return -1;
	}
	
	//helper function for saving user info to database.
	public void saveUser(UserHelperModel aUserHelper){
		
		if(!aUserHelper.userName.equals("") && !aUserHelper.email.equals("") && !aUserHelper.password.equals("") && aUserHelper.role>0) 
		{
			User aUser=new User(aUserHelper.userName,aUserHelper.password,aUserHelper.email,aUserHelper.role);

			userRepository.save(aUser);
		}
	
	}
	
	
	//helper function for deleting user entity from database.
	public void DeleteUser(long id){

		if(id!=0) 
		{	
			userRepository.delete(id);
		}
	
	}

}
