package com.Jahan.Task_Management.helperModel;

public class ProjectHelperModel {
	
	public long projectId;
	public String projectName;
	public String projectDescription;
	public String projectStartTime;
	public String projectEndTime;
	
	public ProjectHelperModel() 
	{
	}
	public ProjectHelperModel(String projectName, String projectDescription,String projectStartTime,String projectEndTime) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.projectStartTime=projectStartTime;
		this.projectEndTime=projectEndTime;
	}
	public long getProjectId() {
		return projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public String getProjectStartTime() {
		return projectStartTime;
	}
	public void setProjectStartTime(String projectStartTime) {
		this.projectStartTime = projectStartTime;
	}
	public String getProjectEndTime() {
		return projectEndTime;
	}
	public void setProjectEndTime(String projectEndTime) {
		this.projectEndTime = projectEndTime;
	}

}
