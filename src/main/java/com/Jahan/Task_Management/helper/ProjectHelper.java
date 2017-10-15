package com.Jahan.Task_Management.helper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.Jahan.Task_Management.repo.*;
import com.Jahan.Task_Management.helperModel.*;
import com.Jahan.Task_Management.model.*;

@Component
public class ProjectHelper {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	
	
	//helper function for saving project info to database.
	public void saveProject(ProjectHelperModel aProjectHelper){
		
		if(!aProjectHelper.projectName.equals("") && !aProjectHelper.projectStartTime.equals("") && !aProjectHelper.projectEndTime.equals("")) 
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
	            Date projectStartTime = formatter.parse(aProjectHelper.projectStartTime);
	            Date projectEndTime = formatter.parse(aProjectHelper.projectEndTime);
	            Project aProject=new Project(aProjectHelper.projectName,aProjectHelper.projectDescription,projectStartTime,projectEndTime,aProjectHelper.createdByuserId);
	            projectRepository.save(aProject);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }

		}
	}
	//helper function for finding a project by id
	public Project getProjectbyID(long id){
		
		Project aProject = new Project();
		if(id!=0) 
		{	
			aProject=projectRepository.findOne(id);
		}
		return aProject;
	}
	//helper function for update a project
	public void updateProject(ProjectHelperModel aProjectHelper){
		Project tempProject=projectRepository.findOne(aProjectHelper.projectId);
		tempProject.setProjectDescription(aProjectHelper.getProjectDescription());
		projectRepository.save(tempProject);
	}
	//helper function for delete project from database.
	public void DeleteProject(long id){

		if(id!=0) 
		{	
			projectRepository.delete(id);
		}
	
	}
}
