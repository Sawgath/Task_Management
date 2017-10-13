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
import com.Jahan.Task_Management.helper.TaskHelper;
import com.Jahan.Task_Management.helper.UserTaskRelHelper;
import com.Jahan.Task_Management.helperModel.DetailAssignTaskHelperModel;
import com.Jahan.Task_Management.helperModel.UserHelperModel;
import com.Jahan.Task_Management.model.Role;
import com.Jahan.Task_Management.model.User;
import com.Jahan.Task_Management.model.UserTaskRel;
import com.Jahan.Task_Management.repo.*;
/*
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
	//To add single user.
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public ModelAndView addUser(@ModelAttribute("aUser") UserHelperModel aUser,Model model){
		boolean temp = LoginHelperT.saveUser(aUser);
		if(temp) return new ModelAndView("redirect:/ListofUser");
		else return new ModelAndView("redirect:/ListofUser-error");
	}
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
			if(aUser.getrole().equals(Role.ADMIN))
			{	
				return "/user-interface/adminmainpage";
			}
			else if(aUser.getrole().equals(Role.MANAGER))
			{
				return "/user-interface/managermainpage";
			}
			else 
			{
				return "/user-interface/staffmainpage";
			}
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
	/*
	 * 	changeRole
	*/
	@RequestMapping(value="/ChangeRole",method=RequestMethod.POST)
	public String changeRole(Model model,@ModelAttribute("changeRoleUser") String userID){
		long num=Long.parseLong(userID);
		User tempUserUpdate=LoginHelperT.getUserInfoByID(num);
		UserHelperModel changeRoleUser =new UserHelperModel();
		changeRoleUser.userId= tempUserUpdate.getuserId();
		changeRoleUser.setpassword(tempUserUpdate.getpassword());
		changeRoleUser.setuserName(tempUserUpdate.getuserName());
		changeRoleUser.active= Integer.toString(tempUserUpdate.getActive());
		changeRoleUser.setrole(tempUserUpdate.getrole());
		model.addAttribute("changeRoleUser",changeRoleUser);
		return "/user-interface/ChangeRole";
	}
	@RequestMapping(value="/ChangedRole",method=RequestMethod.POST)
	public ModelAndView changedRole(Model model,@ModelAttribute("changeRoleUser") UserHelperModel aUser){
		LoginHelperT.updateForChangeUserRole(aUser);
		return new ModelAndView("redirect:/changeRoleList");
	}
	/*
	 * 	changeRoleList
	*/
	@RequestMapping(value="/changeRoleList",method=RequestMethod.GET)
	public String changeRoleList(Model model){
		String str="";
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
	 * 	changeRoleList taskstatuspage
	*/
	@RequestMapping(value="/taskstatuspage",method=RequestMethod.GET)
	public String taskstatuspage(Model model, @ModelAttribute("UserSession") UserHelperModel aSessionUser){
		
		List<DetailAssignTaskHelperModel> datList= new ArrayList<DetailAssignTaskHelperModel>();
		List<UserTaskRel> userTaskRelList= new ArrayList<UserTaskRel>();
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
}

