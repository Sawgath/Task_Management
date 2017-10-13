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
import com.Jahan.Task_Management.helper.*;
import com.Jahan.Task_Management.helperModel.*;
import com.Jahan.Task_Management.model.*;
import com.Jahan.Task_Management.repo.*;

@SessionAttributes("UserSession")
@Controller
public class TaskManageController {

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
	/*
	 * create new task
	 */
	@RequestMapping(value="/NewTask",method=RequestMethod.POST)
	public ModelAndView addNewProject(@ModelAttribute("aTask") TaskHelperModel aTask, @ModelAttribute("UserSession") UserHelperModel aSessionUser){
		if(aTask!=null) 
		{
			aTask.setCreatedByuserId(aSessionUser.getuserId());
			TaskHelperT.saveTask(aTask);
		}
		return new ModelAndView("redirect:/UI");
	}
	@RequestMapping(value="/CreateTask",method=RequestMethod.POST)
	public String newProjectForm(Model model){
		TaskHelperModel aTask=new TaskHelperModel();
		Priority aPriority;
		List<Project> projectList= new ArrayList<Project>();
		for(Project tempProject : ProjectRepositoryT.findAll()){
			projectList.add(tempProject);
		}
		model.addAttribute("aTask",aTask);
		model.addAttribute("allProject",projectList);
		return "/task-interface/createtask";
	}
	/*
	 * //For getting list of project
	 */
	@RequestMapping(value="/ListofTask",method=RequestMethod.GET)
	public String findAllTask(Model model, @ModelAttribute("UserSession") UserHelperModel aSessionUser){
		String delTask="";
		String upTask="";
		TaskHelperModel aTask=new TaskHelperModel();
		model.addAttribute("aTask",aTask);
		model.addAttribute("delTask",delTask);
		model.addAttribute("upTask",upTask);
		List<Task> taskList= new ArrayList<Task>();
		for(Task tempProject : TaskRepositoryT.findAll()){
			if(aSessionUser.getrole().equals(Role.MANAGER))
			{
				if(aSessionUser.userId==tempProject.createdByuserId)
				{
					taskList.add(tempProject);
				}
			}
			else if(aSessionUser.getrole().equals(Role.STAFF))
			{
				//Skip
			}
			else 
			{
				taskList.add(tempProject);	
			}
		}
		model.addAttribute("taskList",taskList);
		return "/task-interface/listoftask";
	}
	/*
	 * //To delete single task.
	*/
	@RequestMapping(value="/deleteTask",method=RequestMethod.POST)
	public ModelAndView deleteTask(Model model,@ModelAttribute("delTask") String delTask){
		long num=Long.parseLong(delTask);
		TaskHelperT.DeleteTask(num);
		return new ModelAndView("redirect:/ListofTask");
	}
	/*
	 * Task Allocated to user
	*/
	@RequestMapping(value="/TaskAssign",method=RequestMethod.GET)
	public String taskAssign(Model model,@ModelAttribute("UserSession") UserHelperModel aSessionUser){
		List<Task> taskList= new ArrayList<Task>();
		for(Task tempProject : TaskRepositoryT.findAll()){
			
			if(aSessionUser.getrole().equals(Role.MANAGER))
			{
				if(aSessionUser.userId==tempProject.createdByuserId)
				{
					taskList.add(tempProject);
				}
			}
			else if(aSessionUser.getrole().equals(Role.STAFF))
			{
				//Skip
			}
			else 
			{
				taskList.add(tempProject);	
			}
			
		}
		List<User> userList= new ArrayList<User>();
		for(User cust : UserRepositoryT.findAll()){
			if(aSessionUser.getrole().equals(Role.MANAGER))
			{
				if(cust.getrole().equals(Role.MANAGER) || cust.getrole().equals(Role.ADMIN))
				{
					//skip
				}
				else
				{
					userList.add(cust);
				}
			}
			else if(aSessionUser.getrole().equals(Role.STAFF))
			{
				//Skip
			}
			else 
			{
				userList.add(cust);
			}
		}
		List<Project> projectList= new ArrayList<Project>();
		for(Project tempProject : ProjectRepositoryT.findAll()){
			if(aSessionUser.getrole().equals(Role.MANAGER))
			{
				if(aSessionUser.userId==tempProject.createdByuserId)
				{
					projectList.add(tempProject);
				}
			}
			else if(aSessionUser.getrole().equals(Role.STAFF))
			{
				//Skip
			}
			else 
			{
				projectList.add(tempProject);
			}
		}
		UserTaskRelHelperModel aUserTaskRelHelperModel= new UserTaskRelHelperModel();
		model.addAttribute("aUserTaskRelHelperModel",aUserTaskRelHelperModel);
		model.addAttribute("projectList",projectList);
		model.addAttribute("userList",userList);
		model.addAttribute("taskList",taskList);
		return "/task-interface/assigntask";
	}
	@RequestMapping(value="/TaskAssign",method=RequestMethod.POST)
	public String AssignedTask(Model model,@ModelAttribute("aUserTaskRelHelperModel") UserTaskRelHelperModel aUserTaskRelHelperModel){
		
		UserTaskRelHelperT.saveUserTaskRel(aUserTaskRelHelperModel);
		return "/task-interface/assigntask";
	}
	/*
	 * assigned task list
	*/
	@RequestMapping(value="/TaskAssignlist",method=RequestMethod.GET)
	public String taskAssignList(Model model ,@ModelAttribute("UserSession") UserHelperModel aSessionUser){
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
		model.addAttribute("datList",datList);
		return "/task-interface/assigntasklist";
	}

}