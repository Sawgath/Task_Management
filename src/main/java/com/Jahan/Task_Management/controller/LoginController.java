package com.Jahan.Task_Management.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.Jahan.Task_Management.helper.LoginHelper;
import com.Jahan.Task_Management.helperModel.UserHelper;
import com.Jahan.Task_Management.model.User;
import com.Jahan.Task_Management.repo.UserRepository;

@Controller
public class LoginController {
	
	@Autowired(required = true)
	UserRepository repository;
	@Autowired
	LoginHelper aloginhelper;
	@RequestMapping(value="/Login",method=RequestMethod.GET)
	public String Login(Model model){
	
		UserHelper aUser=new UserHelper();
		model.addAttribute("aUser",aUser);
		
		return "/Product/Login";
	}
	
	@RequestMapping(value="/Login",method=RequestMethod.POST)
	public ModelAndView process(@ModelAttribute("aUser") UserHelper aUser){
	
		
		String atext= aloginhelper.CheckUser(aUser);
		
		if(atext.equals("Success"))
		{
			//return "redirect: findall";
			return new ModelAndView("redirect:/findall");
		}
		else
		{
			return null;
			
		}
		
		
	}
	

}
