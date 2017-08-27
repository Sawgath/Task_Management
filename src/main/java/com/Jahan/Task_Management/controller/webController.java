package com.Jahan.Task_Management.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Jahan.Task_Management.model.User;
import com.Jahan.Task_Management.repo.UserRepository;
/*
	Test Controller for manipulating user data
*/
@Controller
public class webController {
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping("/save")
	public String process(){
		userRepository.save(new User("Admin","123","Admin@yahoo.com",1));
		userRepository.save(new User("Admin2", "123","admin2@yahoo.com",1));
		userRepository.save(new User("Creep1", "123","Creep1@yahoo.com",2));
		userRepository.save(new User("Creep3", "123","Creep3@yahoo.com",3));
		userRepository.save(new User("Noob","123","Creep3@yahoo.com",3));
		return "Done";
	}
	
	@RequestMapping(value="/findall",method=RequestMethod.GET)
	public String findAll(Model model){
		List<User> userList= new ArrayList<User>();
		for(User cust : userRepository.findAll())
		{	
			userList.add(cust);
		}
		model.addAttribute("UserList",userList);
		return "/login/allusers";
	}
}
