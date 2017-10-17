package com.Jahan.Task_Management.controller;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.Jahan.Task_Management.config.SecurityConfiguration;
import com.Jahan.Task_Management.helper.LoginHelper;
import com.Jahan.Task_Management.helper.TaskHelper;
import com.Jahan.Task_Management.helper.UserTaskRelHelper;
import com.Jahan.Task_Management.helperModel.DetailAssignTaskHelperModel;
import com.Jahan.Task_Management.helperModel.PasswordChngHelperModel;
import com.Jahan.Task_Management.helperModel.UserHelperModel;
import com.Jahan.Task_Management.model.*;
import com.Jahan.Task_Management.repo.*;
/*/
 * 	UserManage Controller for adding user's or deleting user's or updating user's info
*/
@SessionAttributes("UserSession")
@Controller
public class UserManageController {
	@Autowired
	LoginHelper LoginHelperT;
	@Autowired
	TaskHelper TaskHelperT;
	@Autowired
	TaskRepository TaskRepositoryT;
	@Autowired
	ProjectRepository ProjectRepositoryT;
	@Autowired
	UserTaskRelRepository UserTaskRelRepositoryT;
	@Autowired
	UserRepository UserRepositoryT;
	@Autowired
	UserTaskRelHelper UserTaskRelHelperT;
	@Autowired
	private SecurityConfiguration SecurityConfigurationT;
	/*
	 * To add a single user from admin pannel.
	 */
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public ModelAndView addUser(@ModelAttribute("aUser") UserHelperModel aUser,Model model,RedirectAttributes redir){
		boolean temp =LoginHelperT.checkUniqueNameEmail(aUser.getuserName(),aUser.getemail());
		ModelAndView modelAndView = new ModelAndView();
		if(temp && aUser.password.length()>4)
		{
			LoginHelperT.saveUser(aUser);
			redir.addFlashAttribute("successMessage", "User has been added successfully");
			modelAndView.setViewName("redirect:ListofUser");
			return  modelAndView;
		}
		else
		{
			redir.addFlashAttribute("successMessage", "User name or email already exists.Password length must be atleast 5.");
			modelAndView.setViewName("redirect:/ListofUser");
			return modelAndView;
		}
	}
	/*
	 *For getting list of User. Only admin can access it.
	 */
	@RequestMapping(value="/ListofUser",method=RequestMethod.GET)
	public String findAll(Model model ,@ModelAttribute("UserSession") UserHelperModel aSessionUser){
		if(aSessionUser.getrole().equals(Role.ADMIN))
		{
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
		else
		{
			return "/error-interface/403";
		}
	}
	/*
	 * Error page for  getting list of User
	 */
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
	/*
	 *User-wise main page after login. User interface is redirecting based on role
	 */
	@RequestMapping(value="/UI",method=RequestMethod.GET)
	public String userInterface(Model model, @ModelAttribute("UserSession") UserHelperModel aSessionUser){
			if(aSessionUser!=null)
			{
				model.addAttribute("aUser",aSessionUser);
				if(aSessionUser.getrole().equals(Role.ADMIN))
				{	
					return "/user-interface/adminmainpage";
				}
				else if(aSessionUser.getrole().equals(Role.MANAGER))
				{
					return "/user-interface/managermainpage";
				}
				else 
				{
					return "/user-interface/staffmainpage";
				}
			}
			else
			{
				return "/error-interface/403";
			}
	}
	/*
	 *To change password of single user.
	 */
	@RequestMapping(value="/changeUserPassword",method=RequestMethod.GET)
	public String updateUser(Model model,@ModelAttribute("UserSession") UserHelperModel aSessionUser){
		UserHelperModel userUpdate =aSessionUser;
		PasswordChngHelperModel tempPassword=new PasswordChngHelperModel();
		model.addAttribute("UserUpdate",userUpdate);
		model.addAttribute("tempPassword",tempPassword);
		return "/user-interface/updateuser";
	}
	/*
	 *To change password error.
	 */
	@RequestMapping(value="/changeUserPasswordError",method=RequestMethod.GET)
	public String updateUserError(Model model,@ModelAttribute("UserSession") UserHelperModel aSessionUser){
		UserHelperModel userUpdate =aSessionUser;
		PasswordChngHelperModel tempPassword=new PasswordChngHelperModel();
		model.addAttribute("UserUpdate",userUpdate);
		model.addAttribute("tempPassword",tempPassword);
		model.addAttribute("Error",true);
		return "/user-interface/updateuser";
	}
	/*
	 * new password update
	 */
	@RequestMapping(value="/updatedUser",method=RequestMethod.POST)
	public ModelAndView updatedUser(Model model,@ModelAttribute("UserSession") UserHelperModel aSessionUser,@ModelAttribute("tempPassword") PasswordChngHelperModel tempPassword,RedirectAttributes redir){
		ModelAndView modelAndView = new ModelAndView();
		BCryptPasswordEncoder aBCryptPasswordEncoder=SecurityConfigurationT.passwordEncoder();
		String oldPassword =tempPassword.oldPassword;
		String newPassword =tempPassword.newPassword;
		if(oldPassword.equals(newPassword))
		{
			modelAndView.setViewName("redirect:/changeUserPasswordError");
			return modelAndView;
		}
		else if(aBCryptPasswordEncoder.matches(oldPassword,aSessionUser.getpassword()) && !newPassword.equals("") &&  newPassword.length()>2)
		{
			UserHelperModel aUserHelper=new UserHelperModel(UserRepositoryT.findOne(aSessionUser.getuserId()));
			aUserHelper.setpassword(newPassword);
			LoginHelperT.updateUser(aUserHelper);
			redir.addFlashAttribute("successMessage", "You have updated your password.");
			modelAndView.setViewName("redirect:/UI");
			return modelAndView;
		}else 
		{
			modelAndView.setViewName("redirect:/changeUserPasswordError");
			return modelAndView;
		}
	}
	/*
	 * 	To delete single user.
	*/
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	public ModelAndView deleteUser(Model model,@ModelAttribute("DelUser") String userID,RedirectAttributes redir,@ModelAttribute("UserSession") UserHelperModel aSessionUser){
		ModelAndView modelAndView = new ModelAndView();
		long num=Long.parseLong(userID);
		if(aSessionUser.getrole().equals(Role.ADMIN))
		{
			LoginHelperT.deleteUser(num);
			redir.addFlashAttribute("successMessage", "User has been removed successfully");
			modelAndView.setViewName("redirect:ListofUser");
		}
		else
		{
			redir.addFlashAttribute("successMessage", "User is not authorized to remove another user.");
			modelAndView.setViewName("redirect:ListofUser");
		}
		return modelAndView;
	}
	/*
	 * 	Change role of a user
	*/
	@RequestMapping(value="/ChangeRole",method=RequestMethod.POST)
	public ModelAndView changeRole(Model model,@ModelAttribute("changeRoleUser") String userID,@ModelAttribute("UserSession") UserHelperModel aSessionUser, RedirectAttributes redir){
		long num=Long.parseLong(userID);
		ModelAndView modelAndView = new ModelAndView();
		User tempUserUpdate=LoginHelperT.getUserInfoByID(num);
		if(aSessionUser.getrole().equals(Role.ADMIN))
		{
			UserHelperModel changeRoleUser =new UserHelperModel();
			changeRoleUser.userId= tempUserUpdate.getuserId();
			changeRoleUser.setpassword(tempUserUpdate.getpassword());
			changeRoleUser.setuserName(tempUserUpdate.getuserName());
			changeRoleUser.active= Integer.toString(tempUserUpdate.getActive());
			changeRoleUser.setrole(tempUserUpdate.getrole());
			model.addAttribute("changeRoleUser",changeRoleUser);
			modelAndView.setViewName("/user-interface/changerole");
			return modelAndView;
		}
		else
		{
			redir.addFlashAttribute("successMessage", "User is not authorized to change role of another user.");
			modelAndView.setViewName("redirect:ListofUser");
			return modelAndView;
		}
	}
	@RequestMapping(value="/ChangedRole",method=RequestMethod.POST)
	public ModelAndView changedRole(Model model,@ModelAttribute("changeRoleUser") UserHelperModel aUser){
		LoginHelperT.updateForChangeUserRole(aUser);
		return new ModelAndView("redirect:/changeRoleList");
	}
	/*
	 * User Role List
	*/
	@RequestMapping(value="/changeRoleList",method=RequestMethod.GET)
	public String changeRoleList(Model model){
		String changeRoleUser="";
		UserHelperModel aUser=new UserHelperModel();
		model.addAttribute("aUser",aUser);
		model.addAttribute("changeRoleUser",changeRoleUser);
		List<User> userList= new ArrayList<User>();
		for(User cust : UserRepositoryT.findAll()){
			userList.add(cust);
		}
		model.addAttribute("UserList",userList);
		return "/user-interface/changerolelist";
	}
	/*
	 * 	changeRoleList taskstatus. Detail about the all users.
	*/
	@RequestMapping(value="/taskstatuspage",method=RequestMethod.GET)
	public String taskstatuspage(Model model, @ModelAttribute("UserSession") UserHelperModel aSessionUser){
		
		List<DetailAssignTaskHelperModel> datList= new ArrayList<DetailAssignTaskHelperModel>();
		for(UserTaskRel tempuserTaskRel : UserTaskRelRepositoryT.findAll()){
		
			DetailAssignTaskHelperModel aDetailAssignTaskHelperModel=new DetailAssignTaskHelperModel();
			aDetailAssignTaskHelperModel.aUser=UserRepositoryT.findOne(tempuserTaskRel.getUserId());
			aDetailAssignTaskHelperModel.aProject=ProjectRepositoryT.findOne(tempuserTaskRel.getProjectId());
			aDetailAssignTaskHelperModel.aTask=TaskRepositoryT.findOne(tempuserTaskRel.getTaskId());
			aDetailAssignTaskHelperModel.aUserTaskRel=tempuserTaskRel;
			
			if(aSessionUser.getrole().equals(Role.MANAGER))
			{
				if(aSessionUser.userId==aDetailAssignTaskHelperModel.getaProject().createdByuserId)
				{
					datList.add(aDetailAssignTaskHelperModel);
				}
			}
			else if(aSessionUser.getrole().equals(Role.STAFF))
			{
				if(aSessionUser.userId==aDetailAssignTaskHelperModel.getaUserTaskRel().getUserId())
				{
					datList.add(aDetailAssignTaskHelperModel);
				}
			}
			else 
			{
				datList.add(aDetailAssignTaskHelperModel);	
			}
		}
		String aAcceptedflag="";
		String aCompletedflag="";
		model.addAttribute("datList",datList);
		model.addAttribute("aAcceptedflag",aAcceptedflag);
		model.addAttribute("aCompletedflag",aCompletedflag);
		return "/user-interface/staffstatuspage";
	}
	/*
	 * accepting the task 
	 */
	@RequestMapping(value="/taskstatusacceptupdate",method=RequestMethod.POST)
	public ModelAndView taskstatusupdateAccepted(Model model, @ModelAttribute("UserSession") UserHelperModel aSessionUser,@ModelAttribute("aAcceptedflag") String aAcceptedflag){
		for(UserTaskRel tempuserTaskRel : UserTaskRelRepositoryT.findAll()){
			if(tempuserTaskRel.getUserTaskRelId()==Integer.parseInt(aAcceptedflag));
			{
				tempuserTaskRel.setTaskAccepted(1);	
				UserTaskRelRepositoryT.save(tempuserTaskRel);
			}
		}
		return new ModelAndView("redirect:/taskstatuspage");
	}
	/*
	 * completing the task 
	 */
	@RequestMapping(value="/taskstatuscompleteupdate",method=RequestMethod.POST)
	public ModelAndView taskstatusupdateCompleted(Model model, @ModelAttribute("UserSession") UserHelperModel aSessionUser,@ModelAttribute("aCompletedflag") String aCompletedflag){
		for(UserTaskRel tempuserTaskRel : UserTaskRelRepositoryT.findAll()){
			if(tempuserTaskRel.getUserTaskRelId()==Integer.parseInt(aCompletedflag));
			{
				tempuserTaskRel.setTaskCompleted(1);	
				UserTaskRelRepositoryT.save(tempuserTaskRel);
			}
		}
		return new ModelAndView("redirect:/taskstatuspage");
	}
	/*
	 * Exception handle  
	 */
	 @ExceptionHandler(Exception.class)
	 public ModelAndView handleError(HttpServletRequest request, Exception e)   {
        return new ModelAndView("/error-interface/403");
	 }
}