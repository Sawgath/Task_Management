package com.Jahan.Task_Management.helper;

import com.Jahan.Task_Management.model.User;
import com.Jahan.Task_Management.repo.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Jahan.Task_Management.helperModel.UserHelper;

@Component
public class LoginHelper {
	
	@Autowired
	UserRepository userRepos;
	
	//User's Name and password checking.
	public String CheckUser(UserHelper aUserHelper){
		
		List<User> UserList = userRepos.findByuserName(aUserHelper.userName);
		
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
	
	
	//helper function for saving user info to database.
	public void saveUser(UserHelper aUserHelper){
		
		if(!aUserHelper.userName.equals("") && !aUserHelper.email.equals("") && !aUserHelper.password.equals("") && aUserHelper.role>0) 
		{
			User aUser=new User(aUserHelper.userName,aUserHelper.password,aUserHelper.email,aUserHelper.role);

			userRepos.save(aUser);
		}
	
	}
	
	
	//helper function for deleting user entity from database.
	public void DeleteUser(long id){

		if(id!=0) 
		{	
			userRepos.delete(id);
		}
	
	}

}
