package com.Jahan.Task_Management.helper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.Jahan.Task_Management.helperModel.ProjectHelperModel;
import com.Jahan.Task_Management.helperModel.TaskHelperModel;
import com.Jahan.Task_Management.model.Project;
import com.Jahan.Task_Management.model.Task;
import com.Jahan.Task_Management.repo.TaskRepository;

@Component
public class TaskHelper {

	@Autowired
	TaskRepository TaskRepositoryT;
	public void DeleteTask(long id){
		if(id!=0) 
		{	
			TaskRepositoryT.delete(id);
		}
	}
	
public void saveTask(TaskHelperModel aTaskHelperModel){
		
		if(!aTaskHelperModel.taskName.equals("") && !aTaskHelperModel.taskStartTime.equals("") && !aTaskHelperModel.taskEndTime.equals("")) 
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
	            Date projectStartTime = formatter.parse(aTaskHelperModel.taskStartTime);
	            Date projectEndTime = formatter.parse(aTaskHelperModel.taskEndTime);
	            /*
	             * Task constructor parameter
	             * long parentId, long projectId, String taskName,String taskDescription,String taskGoal, String taskStartTime, String taskEndTime
	             */
	            Task aTask=new Task(0, aTaskHelperModel.projectId, aTaskHelperModel.taskName,aTaskHelperModel.taskDescription,
	            					aTaskHelperModel.taskGoal, projectStartTime, projectEndTime);
	            TaskRepositoryT.save(aTask);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		}
	}
}
