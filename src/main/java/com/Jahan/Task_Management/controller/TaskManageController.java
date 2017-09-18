package com.Jahan.Task_Management.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.Jahan.Task_Management.helper.*;
import com.Jahan.Task_Management.helperModel.*;
import com.Jahan.Task_Management.model.*;
import com.Jahan.Task_Management.repo.*;

@Controller
public class TaskManageController {

	@Autowired
	TaskHelper TaskHelperT;
	@Autowired
	TaskRepository TaskRepositoryT;
	@Autowired
	ProjectRepository ProjectRepositoryT;

	@RequestMapping(value="/NewTask",method=RequestMethod.POST)
	public ModelAndView addNewProject(@ModelAttribute("aTask") TaskHelperModel aTask){
		
		TaskHelperT.saveTask(aTask);
		return new ModelAndView("redirect:/UI");
	}
	
	@RequestMapping(value="/CreateTask",method=RequestMethod.POST)
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

	//For getting list of project
	@RequestMapping(value="/ListofTask",method=RequestMethod.GET)
	public String findAllTask(Model model){
		String delTask="";
		TaskHelperModel aTask=new TaskHelperModel();
		model.addAttribute("aTask",aTask);
		model.addAttribute("delTask",delTask);
		List<Task> taskList= new ArrayList<Task>();
		for(Task tempProject : TaskRepositoryT.findAll()){
			taskList.add(tempProject);
		}
		model.addAttribute("taskList",taskList);
		return "/project-interface/listoftask";
	}
}
