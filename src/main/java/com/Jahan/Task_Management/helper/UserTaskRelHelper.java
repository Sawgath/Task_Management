package com.Jahan.Task_Management.helper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.Jahan.Task_Management.helperModel.TaskHelperModel;
import com.Jahan.Task_Management.helperModel.UserTaskRelHelperModel;
import com.Jahan.Task_Management.model.Task;
import com.Jahan.Task_Management.model.UserTaskRel;
import com.Jahan.Task_Management.repo.UserTaskRelRepository;

@Component
public class UserTaskRelHelper {

@Autowired
UserTaskRelRepository UserTaskRelRepositoryT;
	
public void saveUserTaskRel(UserTaskRelHelperModel aUserTaskRelHelperModel){
		
		if(aUserTaskRelHelperModel.taskId!=0 && aUserTaskRelHelperModel.projectId!=0 && aUserTaskRelHelperModel.userId!=0 && !aUserTaskRelHelperModel.taskStartTime.equals("") && !aUserTaskRelHelperModel.taskEndTime.equals("")) 
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
	            Date projectStartTime = formatter.parse(aUserTaskRelHelperModel.taskStartTime);
	            Date projectEndTime = formatter.parse(aUserTaskRelHelperModel.taskEndTime);
	            /*
	             * Task constructor parameter
	             * long parentId, long projectId, String taskName,String taskDescription,String taskGoal, String taskStartTime, String taskEndTime
	             */
	            UserTaskRel aUserTaskRel=new UserTaskRel(aUserTaskRelHelperModel,projectStartTime,projectEndTime);
	            UserTaskRelRepositoryT.save(aUserTaskRel);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		}
	}
}
	

