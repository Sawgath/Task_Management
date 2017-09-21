package com.Jahan.Task_Management.controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.unbescape.html.HtmlEscape;

import com.Jahan.Task_Management.config.SecurityConfiguration;
import com.Jahan.Task_Management.helper.LoginHelper;
import com.Jahan.Task_Management.helperModel.UserHelperModel;
import com.Jahan.Task_Management.model.Role;
import com.Jahan.Task_Management.model.User;
import com.Jahan.Task_Management.repo.UserRepository;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
/*
	Test Controller for manipulating user data
*/
@Controller
public class webController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	LoginHelper LoginHelperT;
	@Autowired
	SecurityConfiguration SecurityConfigurationT;
	@RequestMapping("/save")
	public String process(){
		
		BCryptPasswordEncoder aBCryptPasswordEncoder=SecurityConfigurationT.passwordEncoder();
		userRepository.save(new User("Admin",aBCryptPasswordEncoder.encode("123"),"Admin@yahoo.com",Role.ADMIN));
		userRepository.save(new User("Admin2",aBCryptPasswordEncoder.encode("123"),"admin2@yahoo.com",Role.ADMIN));
		userRepository.save(new User("Boss",aBCryptPasswordEncoder.encode("123"),"Boss@yahoo.com",Role.MANAGER));
		userRepository.save(new User("Creep1",aBCryptPasswordEncoder.encode("123"),"Creep1@yahoo.com",Role.STAFF));
		userRepository.save(new User("Creep2",aBCryptPasswordEncoder.encode("123"),"Creep2@yahoo.com",Role.STAFF));
		return "Done";
	}
	
	@RequestMapping("/registration")
	public ModelAndView registration(Model model){
		
		User aUser=new User();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", aUser);
		modelAndView.setViewName("/user-interface/registration");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
	 		ModelAndView modelAndView = new ModelAndView();
	 		User userExists = LoginHelperT.findUserByEmail(user.getemail());
	 		if (userExists != null) {
	 			bindingResult
	 					.rejectValue("email", "error.user",
	 							"There is already a user registered with the email provided");
	 		}
	 		if (bindingResult.hasErrors()) {
	 			modelAndView.setViewName("/user-interface/registration");
	 	} else {
	 			LoginHelperT.saveAUser(user);
	 			modelAndView.addObject("successMessage", "User has been registered successfully");
	 			modelAndView.addObject("user", new User());
	 			modelAndView.setViewName("/user-interface/registration");
	 			
	 		}
	 		return modelAndView;
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

	
    /** Error page. */
    @RequestMapping("/403.html")
    public String forbidden() {
        return "/error-interface/403";
    }
    
    /*
     * testing api
     */
    
    @RequestMapping("/API")
    public void APIFunction() {
    	
  
    }
    

/** Error page. */
    @RequestMapping("/error.html")
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", "Error " + request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("<ul>");
        while (throwable != null) {
            errorMessage.append("<li>").append(HtmlEscape.escapeHtml5(throwable.getMessage())).append("</li>");
            throwable = throwable.getCause();
        }
        errorMessage.append("</ul>");
        model.addAttribute("errorMessage", errorMessage.toString());
        return "/error-interface/error";
    }
    
}
