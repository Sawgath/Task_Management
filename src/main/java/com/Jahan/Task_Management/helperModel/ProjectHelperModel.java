package com.Jahan.Task_Management.helperModel;
import com.Jahan.Task_Management.model.Project;

public class ProjectHelperModel {	
	public long projectId;
	public String projectName;
	public String projectDescription;
	public String projectStartTime;
	public String projectEndTime;
	public String usingFlag;
	public long createdByuserId;
	public ProjectHelperModel() 
	{
	}
	public ProjectHelperModel(String projectName, String projectDescription,String projectStartTime,String projectEndTime) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.projectStartTime=projectStartTime;
		this.projectEndTime=projectEndTime;
	}
	public ProjectHelperModel(Project aProject) {
		this.projectId=aProject.getProjectId();
		this.projectName = aProject.getProjectName();
		this.projectDescription = aProject.getProjectDescription();
		this.projectStartTime=aProject.getProjectStartTime().toString();
		this.projectEndTime=aProject.getProjectEndTime().toString();
	}
	public long getprojectId() {
		return projectId;
	}
	public void setprojectId(long projectId) {
		this.projectId = projectId;
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
	
	public String getUsingFlag() {
		return usingFlag;
	}

	public void setUsingFlag(String usingFlag) {
		this.usingFlag = usingFlag;
	}

	public long getCreatedByuserId() {
		return createdByuserId;
	}

	public void setCreatedByuserId(long createdByuserId) {
		this.createdByuserId = createdByuserId;
	}
}
