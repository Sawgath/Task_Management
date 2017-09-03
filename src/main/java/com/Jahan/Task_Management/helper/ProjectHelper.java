package com.Jahan.Task_Management.helper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.Jahan.Task_Management.repo.UserRepository;
import com.Jahan.Task_Management.helperModel.ProjectHelperModel;
import com.Jahan.Task_Management.model.Project;
import com.Jahan.Task_Management.model.User;
import com.Jahan.Task_Management.repo.ProjectRepository;

@Component
public class ProjectHelper {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	
	//helper function for saving user info to database.
	public void saveProject(ProjectHelperModel aProjectHelper){
		
		if(!aProjectHelper.projectName.equals("") && !aProjectHelper.projectStartTime.equals("") && !aProjectHelper.projectEndTime.equals("")) 
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
	            Date projectStartTime = formatter.parse(aProjectHelper.projectStartTime);
	            Date projectEndTime = formatter.parse(aProjectHelper.projectEndTime);
	            Project aProject=new Project(aProjectHelper.projectName,aProjectHelper.projectDescription,projectStartTime,projectEndTime);
	            projectRepository.save(aProject);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }

		}
	}
	
	public void DeleteProject(long id){

		if(id!=0) 
		{	
			projectRepository.delete(id);
		}
	
	}
}
