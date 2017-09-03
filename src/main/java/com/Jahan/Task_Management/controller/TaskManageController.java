package com.Jahan.Task_Management.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.Jahan.Task_Management.helper.ProjectHelper;
import com.Jahan.Task_Management.helper.TaskHelper;
import com.Jahan.Task_Management.helperModel.ProjectHelperModel;
import com.Jahan.Task_Management.helperModel.TaskHelperModel;
import com.Jahan.Task_Management.model.Project;
import com.Jahan.Task_Management.repo.TaskRepository;
import com.Jahan.Task_Management.repo.ProjectRepository;

@Controller
public class TaskManageController {

	@Autowired
	TaskHelper TaskHelperT;
	@Autowired
	TaskRepository TaskRepositoryT;
	@Autowired
	ProjectRepository ProjectRepositoryT;

	@RequestMapping(value="/NewTask",method=RequestMethod.POST)
	public String addNewProject(@ModelAttribute("aTask") TaskHelperModel aTask){
		
		TaskHelperT.saveTask(aTask);
		return "/task-interface/createtask";
	}
	
	@RequestMapping(value="/NewTask",method=RequestMethod.GET)
	public String newProjectForm(Model model){
		TaskHelperModel aTask=new TaskHelperModel();
		List<Project> projectList= new ArrayList<Project>();
		for(Project tempProject : ProjectRepositoryT.findAll()){
			projectList.add(tempProject);
		}
		model.addAttribute("aTask",aTask);
		model.addAttribute("allProject",projectList);
		return "/task-interface/createtask";
	}
	
}
