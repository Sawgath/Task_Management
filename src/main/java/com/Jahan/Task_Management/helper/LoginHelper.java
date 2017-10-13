package com.Jahan.Task_Management.helper;
import com.Jahan.Task_Management.model.Role;
import com.Jahan.Task_Management.model.User;
import com.Jahan.Task_Management.repo.UserRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.Jahan.Task_Management.config.SecurityConfiguration;
import com.Jahan.Task_Management.helperModel.UserHelperModel;

@Component
public class LoginHelper {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private SecurityConfiguration SecurityConfigurationT;
	
	//User's Name and password checking.
	public String checkUser(UserHelperModel aUserHelper){
		
		BCryptPasswordEncoder aBCryptPasswordEncoder=SecurityConfigurationT.passwordEncoder();
		
		List<User> UserList = userRepository.findByuserName(aUserHelper.userName);
		
		if(UserList.size()==1) 
		{
			for(User auser : UserList)
			{
				if(aBCryptPasswordEncoder.matches(aUserHelper.getpassword(),auser.getpassword()))
				{
					//Matched password for user.
					return "Success";
				}
			}
		}
		return "Fail";
	}
	
	public boolean checkUniqueNameEmail(String name,String email){
		
		List<User> UserList1 = userRepository.findByuserName(name);
		List<User> UserList2 = userRepository.findByemail(email);
		if(UserList1.size()>0) 
		{ 
			return false;			
		}
		
		if(UserList2.size()>0) 
		{ 
			return false;			
		}
		
		return true;
	}
	
	public long getUserId(UserHelperModel aUserHelper){
		BCryptPasswordEncoder aBCryptPasswordEncoder=SecurityConfigurationT.passwordEncoder();
		
		List<User> UserList = userRepository.findByuserName(aUserHelper.userName);
		
		if(UserList.size()==1) 
		{
			for(User auser : UserList)
			{
				if(aBCryptPasswordEncoder.matches(aUserHelper.getpassword(),auser.getpassword()))
				{
					//Matched password for user.
					return auser.getuserId();
				}
			}
		}
		return -1;
	}
	
	public User getUserInfoByID(long userID){
		User aUser = userRepository.findByuserId(userID);
		return aUser;
	}
	//helper function for saving user info to database.
	public boolean saveUser(UserHelperModel aUserHelper){
		
		if(checkUniqueNameEmail(aUserHelper.getuserName(),aUserHelper.getemail())==true) 
		{
			if(!aUserHelper.userName.equals("") && !aUserHelper.email.equals("") && !aUserHelper.password.equals("")) 
			{
				User aUser=new User(aUserHelper.userName,aUserHelper.password,aUserHelper.email,aUserHelper.role);
				aUser.setpassword(new BCryptPasswordEncoder().encode(aUserHelper.getpassword()));
				aUser.setrole(Role.STAFF);
				userRepository.save(aUser);
				
				return true;
			}
		}
		return false;
	}
	
	public User findUserByEmail(String email) {
			return userRepository.findByEmail(email);
	}

	public void saveAUser(User user) {	
		if(checkUniqueNameEmail(user.getuserName(),user.getemail())==true) 
		{
			BCryptPasswordEncoder aBCryptPasswordEncoder=SecurityConfigurationT.passwordEncoder();
	 		user.setpassword(aBCryptPasswordEncoder.encode(user.getpassword()));
	 		user.setrole(Role.MANAGER);
	 		userRepository.save(user);
		}
	}
	
	public void updateUser(UserHelperModel user) {	
			BCryptPasswordEncoder aBCryptPasswordEncoder=SecurityConfigurationT.passwordEncoder();
	 		user.setpassword(aBCryptPasswordEncoder.encode(user.getpassword()));
	 		User tempUser=userRepository.findOne(user.getuserId());
	 		tempUser.setpassword(user.getpassword());
	 		tempUser.setuserName(user.getuserName());
	 		tempUser.setActive(Integer.parseInt(user.active));
	 		userRepository.save(tempUser);
	}
	
	public void updateForChangeUserRole(UserHelperModel user) {	
 		User tempUser=userRepository.findOne(user.getuserId());
 		tempUser.setActive(Integer.parseInt(user.active));
 		tempUser.setrole(user.getrole());
 		userRepository.save(tempUser);
	}
	//helper function for deleting user entity from database.
	public void deleteUser(long id){
		if(id!=0) 
		{	
			userRepository.delete(id);
		}
	}
}
