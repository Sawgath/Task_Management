package com.Jahan.Task_Management.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	/*
	 * For create new project
	 */
	@RequestMapping(value="/NewProject",method=RequestMethod.POST)
	public ModelAndView addNewProject(@ModelAttribute("aProject") ProjectHelperModel aProject,@ModelAttribute("UserSession") UserHelperModel aSessionUser,RedirectAttributes redir){
		ModelAndView modelAndView = new ModelAndView();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date projectStartTime = null;
		Date projectEndTime = null;
		try {
			projectStartTime = formatter.parse(aProject.projectStartTime);
			projectEndTime = formatter.parse(aProject.projectEndTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(aProject!=null  && (projectEndTime.compareTo(projectStartTime) > 0)) 
		{
			aProject.setCreatedByuserId(aSessionUser.getuserId());
			projectHelper.saveProject(aProject);
			redir.addFlashAttribute("successMessage", "A Project has been added successfully");
		}
		else 
		{
			redir.addFlashAttribute("successMessage", "You have added invalid information to add object. or the project is already exists.You have to assign minimum one day for creating project.");
		}
		modelAndView.setViewName("redirect:/UI");
		return modelAndView;
	}
	@RequestMapping(value="/CreateProject",method=RequestMethod.POST)
	public String newProjectForm(Model model){
		ProjectHelperModel aProject=new ProjectHelperModel();
		model.addAttribute("aProject",aProject);
		return "/project-interface/createproject";
	}
	/*
	 * For getting list of project
	 */
	@RequestMapping(value="/ListofProject",method=RequestMethod.GET)
	public String findAllProject(Model model, @ModelAttribute("UserSession") UserHelperModel aSessionUser){
		String delProject="";
		String upProject="";
		ProjectHelperModel aProject=new ProjectHelperModel();
		model.addAttribute("aUser",aProject);
		model.addAttribute("upProject",upProject);
		model.addAttribute("delProject",delProject);
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
		model.addAttribute("projectList",projectList);
		return "/project-interface/listofproject";
	}
	/*
	 * //To update project
	 */
	@RequestMapping(value="/updateProject",method=RequestMethod.POST)
	public String updateProject(Model model,@ModelAttribute("upProject") String upProject){
		long num=Long.parseLong(upProject);
		Project aProject = new Project();
		aProject = projectHelper.getProjectbyID(num);
		ProjectHelperModel aProjectHelperModel= new ProjectHelperModel(aProject);
		model.addAttribute("aProjectHelperModel",aProjectHelperModel);
		return "/project-interface/updateproject";
	}
	@RequestMapping(value="/updatedProject",method=RequestMethod.POST)
	public ModelAndView updatedProject(Model model,@ModelAttribute("aProjectHelperModel") ProjectHelperModel aProjectHelperModel){
		projectHelper.updateProject(aProjectHelperModel);
		return new ModelAndView("redirect:/ListofProject");
	}
	/*
	 * To delete single user.
	 */
	@RequestMapping(value="/deleteProject",method=RequestMethod.POST)
	public ModelAndView deleteProject(Model model,@ModelAttribute("delProject") String projectID){
		long num=Long.parseLong(projectID);
		projectHelper.DeleteProject(num);
		return new ModelAndView("redirect:/ListofProject");
	}
	/*
	 * Handle Exception
	 */
	@ExceptionHandler(Exception.class)
	 public ModelAndView handleError(HttpServletRequest request, Exception e)   {
       return new ModelAndView("/error-interface/403");
	 }
}
