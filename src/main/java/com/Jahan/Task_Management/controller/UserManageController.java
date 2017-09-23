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
		String updateUser="";
		UserHelperModel aUser=new UserHelperModel();
		model.addAttribute("aUser",aUser);
		model.addAttribute("DelUser",str);
		model.addAttribute("updateUser",updateUser);
		List<User> userList= new ArrayList<User>();
		model.addAttribute("Error",false);
		for(User cust : UserRepositoryT.findAll()){
			userList.add(cust);
		}
		model.addAttribute("UserList",userList);
		return "/login/userlist";
	}
	
	@RequestMapping(value="/ListofUser-error",method=RequestMethod.GET)
	public String findAllError(Model model){
		String str="";
		String updateUser="";
		UserHelperModel aUser=new UserHelperModel();
		model.addAttribute("aUser",aUser);
		model.addAttribute("DelUser",str);
		model.addAttribute("updateUser",updateUser);
		List<User> userList= new ArrayList<User>();
		model.addAttribute("Error",true);
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
	
	
	//To update single user.
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public String updateUser(Model model,@ModelAttribute("updateUser") String userID){
		long num=Long.parseLong(userID);
		User tempUserUpdate=LoginHelperT.getUserInfoByID(num);
		UserHelperModel userUpdate =new UserHelperModel();
		userUpdate.userId= tempUserUpdate.getuserId();
		userUpdate.setpassword(tempUserUpdate.getpassword());
		userUpdate.setuserName(tempUserUpdate.getuserName());
		userUpdate.active= Integer.toString(tempUserUpdate.getActive());
		
		model.addAttribute("UserUpdate",userUpdate);
		return "/user-interface/updateuser";
	}
	
	@RequestMapping(value="/updatedUser",method=RequestMethod.POST)
	public ModelAndView updatedUser(Model model,@ModelAttribute("UserUpdate") UserHelperModel aUser){
		

		LoginHelperT.updateUser(aUser);
		
		return new ModelAndView("redirect:/ListofUser");
	}
	
	//To delete single user.
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	public ModelAndView deleteUser(Model model,@ModelAttribute("DelUser") String userID){
		long num=Long.parseLong(userID);
		LoginHelperT.deleteUser(num);
		return new ModelAndView("redirect:/ListofUser");
	}
	
	//To add single user.
		@RequestMapping(value="/addUser",method=RequestMethod.POST)
		public ModelAndView addUser(@ModelAttribute("aUser") UserHelperModel aUser,Model model){
			boolean temp = LoginHelperT.saveUser(aUser);
			if(temp) return new ModelAndView("redirect:/ListofUser");
			else return new ModelAndView("redirect:/ListofUser-error");
	}
		
	
}
