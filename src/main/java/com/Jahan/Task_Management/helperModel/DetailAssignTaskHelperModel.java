package com.Jahan.Task_Management.helperModel;

import com.Jahan.Task_Management.model.*;

public class DetailAssignTaskHelperModel {
	
	public User aUser;
	public Project aProject;
	public Task aTask;
	public UserTaskRel aUserTaskRel;
	
	public DetailAssignTaskHelperModel()
	{
		
	}

	public User getaUser() {
		return aUser;
	}

	public void setaUser(User aUser) {
		this.aUser = aUser;
	}

	public Project getaProject() {
		return aProject;
	}

	public void setaProject(Project aProject) {
		this.aProject = aProject;
	}

	public Task getaTask() {
		return aTask;
	}

	public void setaTask(Task aTask) {
		this.aTask = aTask;
	}

	public UserTaskRel getaUserTaskRel() {
		return aUserTaskRel;
	}

	public void setaUserTaskRel(UserTaskRel aUserTaskRel) {
		this.aUserTaskRel = aUserTaskRel;
	}
	

}
