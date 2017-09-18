package com.Jahan.Task_Management.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.Jahan.Task_Management.helper.LoginHelper;
import com.Jahan.Task_Management.helperModel.UserHelperModel;
import com.Jahan.Task_Management.model.User;
import com.Jahan.Task_Management.repo.UserRepository;
/*
 * 	UserManage Controller for adding user's or deleting user's or updating user's info
*/
@SessionAttributes("UserSession")
@Controller
public class UserManageController {
	@Autowired
	UserRepository UserRepositoryT;
	@Autowired
	LoginHelper LoginHelperT;
	
	//For getting list of User
	@RequestMapping(value="/ListofUser",method=RequestMethod.GET)
	public String findAll(Model model){
		String str="";
		UserHelperModel aUser=new UserHelperModel();
		model.addAttribute("aUser",aUser);
		model.addAttribute("DelUser",str);
		List<User> userList= new ArrayList<User>();
		model.addAttribute("Error",false);
		for(User cust : UserRepositoryT.findAll()){
			userList.add(cust);
		}
		model.addAttribute("UserList",userList);
		return "/login/userlist";
	}
	
	//For getting user interface
	@RequestMapping(value="/UI",method=RequestMethod.GET)
	public String userInterface(Model model, @ModelAttribute("UserSession") UserHelperModel aUser){
			model.addAttribute("aUser",aUser);
			return "/user-interface/membermainpage";
	}
	
	//To delete single user.
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	public ModelAndView deleteUser(Model model,@ModelAttribute("DelUser") String userID){
		long num=Long.parseLong(userID);
		LoginHelperT.deleteUser(num);
		return new ModelAndView("redirect:/ListofUser");
	}
	
	
	
	
}
