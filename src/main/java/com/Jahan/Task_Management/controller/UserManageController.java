package com.Jahan.Task_Management.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Jahan.Task_Management.helper.LoginHelper;
import com.Jahan.Task_Management.helperModel.UserHelper;
import com.Jahan.Task_Management.model.User;
import com.Jahan.Task_Management.repo.UserRepository;

@Controller
public class UserManageController {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	LoginHelper aloginhelper;
	
	@RequestMapping(value="/ListofUser",method=RequestMethod.GET)
	public String findAll(Model model){
		
		String str="";
		UserHelper aUser=new UserHelper();
		model.addAttribute("aUser",aUser);
		model.addAttribute("DelUser",str);
		List<User> userList= new ArrayList<User>();
		
		
		
		for(User cust : repository.findAll()){
			
			userList.add(cust);
		}
		model.addAttribute("UserList",userList);
		return "/Product/UserList";
	}
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	public ModelAndView deleteUser(Model model,@ModelAttribute("DelUser") String userID){
		
		long num=Long.parseLong(userID);
		aloginhelper.DeleteUser(num);
		////////////////////////
		
		
		
		return new ModelAndView("redirect:/ListofUser");
	}
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public ModelAndView addUser(@ModelAttribute("aUser") UserHelper aUser){
		
		aloginhelper.saveUser(aUser);
		
		return new ModelAndView("redirect:/ListofUser");
	}

}
