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

/*
 * 	ProjectManageController for adding Project's or deleting Project or updating Project info
*/
@SessionAttributes("UserSession")
@Controller
public class ProjectManageController {
	@Autowired
	ProjectRepository ProjectRepositoryT;
	@Autowired
	ProjectHelper projectHelper;
	
	@RequestMapping(value="/NewProject",method=RequestMethod.POST)
	public ModelAndView addNewProject(@ModelAttribute("aProject") ProjectHelperModel aProject){
		if(aProject!=null) {
			projectHelper.saveProject(aProject);
		}
		return new ModelAndView("redirect:/UI");
	}
	
	
	@RequestMapping(value="/CreateProject",method=RequestMethod.POST)
	public String newProjectForm(Model model){
		ProjectHelperModel aProject=new ProjectHelperModel();
		model.addAttribute("aProject",aProject);
		return "/project-interface/createproject";
	}
	
	//For getting list of project
		@RequestMapping(value="/ListofProject",method=RequestMethod.GET)
		public String findAllProject(Model model){
			String delProject="";
			ProjectHelperModel aProject=new ProjectHelperModel();
			model.addAttribute("aUser",aProject);
			model.addAttribute("delProject",delProject);
			List<Project> projectList= new ArrayList<Project>();
			for(Project tempProject : ProjectRepositoryT.findAll()){
				projectList.add(tempProject);
			}
			model.addAttribute("projectList",projectList);
			return "/project-interface/listofproject";
	}
	
	//To delete single user.
	@RequestMapping(value="/deleteProject",method=RequestMethod.POST)
	public ModelAndView deleteUser(Model model,@ModelAttribute("delProject") String projectID){
		long num=Long.parseLong(projectID);
		projectHelper.DeleteProject(num);
		return new ModelAndView("redirect:/ListofProject");
	}
}
