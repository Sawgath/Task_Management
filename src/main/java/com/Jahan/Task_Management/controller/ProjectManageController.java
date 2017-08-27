package com.Jahan.Task_Management.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.Jahan.Task_Management.helper.ProjectHelper;
import com.Jahan.Task_Management.helperModel.ProjectHelperModel;
/*
 * 	ProjectManageController for adding Project's or deleting Project or updating Project info
*/
@SessionAttributes("UserSession")
@Controller
public class ProjectManageController {
	
	@Autowired
	ProjectHelper projectHelper;
	
	@RequestMapping(value="/NewProject",method=RequestMethod.POST)
	public String addNewProject(@ModelAttribute("aProject") ProjectHelperModel aProject){
		if(aProject!=null) {
			projectHelper.saveProject(aProject);
		}
		return "";
	}
	
	@RequestMapping(value="/NewProject",method=RequestMethod.GET)
	public String newProjectForm(Model model){
		ProjectHelperModel aProject=new ProjectHelperModel();
		model.addAttribute("aProject",aProject);
		return "/project-interface/createproject";
	}
	
	
}
