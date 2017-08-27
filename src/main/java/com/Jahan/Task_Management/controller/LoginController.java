package com.Jahan.Task_Management.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Jahan.Task_Management.helper.LoginHelper;
import com.Jahan.Task_Management.helperModel.UserHelper;
import com.Jahan.Task_Management.repo.UserRepository;

//Login Controller for checking and validation of user input

@Controller
public class LoginController {
	
	
	
	@Autowired(required = true)
	UserRepository userrepo;
	
	
	
	
	
	@RequestMapping(value="/Login",method=RequestMethod.GET)
	public String Login(Model model){
	
		UserHelper aUser=new UserHelper();
		model.addAttribute("aUser",aUser);
		
		
		// return view of the login page.
		return "/Product/Login";
	}
	
	
	@RequestMapping(value="/Login",method=RequestMethod.POST)
	public ModelAndView process(@ModelAttribute("aUser") UserHelper aUser){
	
		
		LoginHelper aloginhelper = new LoginHelper();
		String atext= aloginhelper.CheckUser(aUser);
		
		if(atext.equals("Success"))
		{
			// redirect to find all user page.
			return new ModelAndView("redirect:/findall");
		}
		else
		{
			return null;
			
		}
	}
}
