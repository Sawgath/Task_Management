package com.Jahan.Task_Management.helperModel;

public class UserTaskRelHelperModel {
	public long userTaskRelId;
	public long projectId;
	public long taskId;
	public long userId;
	public String taskAssignDescription;
	public long taskAccepted;
	public long taskCompleted;
	public String taskStartTime;
	public String taskEndTime;
	
	public UserTaskRelHelperModel()
	{	
	}
	public UserTaskRelHelperModel(long userTaskRelId,long projectId,long taskId,long userId,long taskAccepted,long taskCompleted, String taskStartTime, String taskEndTime)
	{
		this.projectId = projectId;
		this.taskId = taskId;
		this.userId = userId;
		this.taskAccepted = taskCompleted;
		this.taskStartTime = taskStartTime;
		this.taskEndTime = taskEndTime;	
	}
	public long getUserTaskRelId() {
		return userTaskRelId;
	}
	public void setUserTaskRelId(long userTaskRelId) {
		this.userTaskRelId = userTaskRelId;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getTaskAssignDescription() {
		return taskAssignDescription;
	}
	public void setTaskAssignDescription(String taskAssignDescription) {
		this.taskAssignDescription = taskAssignDescription;
	}
	public long getTaskAccepted() {
		return taskAccepted;
	}
	public void setTaskAccepted(long taskAccepted) {
		this.taskAccepted = taskAccepted;
	}
	public long getTaskCompleted() {
		return taskCompleted;
	}
	public void setTaskCompleted(long taskCompleted) {
		this.taskCompleted = taskCompleted;
	}
	public String getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public String getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
}
