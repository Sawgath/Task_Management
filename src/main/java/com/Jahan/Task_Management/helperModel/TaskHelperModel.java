package com.Jahan.Task_Management.helperModel;
import com.Jahan.Task_Management.model.Priority;

public class TaskHelperModel {	
	public long taskId;
	public long parentId;
	public long projectId;
	public String taskName;
	public String taskDescription;
	public String taskGoal;
	public String taskStartTime;
	public String taskEndTime;
	public Priority taskPriority;
	public String usingFlag;
	public long createdByuserId;	
	public TaskHelperModel() 
	{
	}
	public TaskHelperModel(long taskId, long parentId, long projectId, String taskName,String taskDescription,String taskGoal, String taskStartTime, String taskEndTime) {
		this.taskId = taskId;
		this.parentId = parentId;
		this.projectId=projectId;
		this.taskName=taskName;
		this.taskDescription=taskDescription;
		this.taskGoal=taskGoal;
		this.taskStartTime=taskStartTime;
		this.taskEndTime=taskEndTime;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public String getTaskGoal() {
		return taskGoal;
	}
	public void setTaskGoal(String taskGoal) {
		this.taskGoal = taskGoal;
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
	public Priority getTaskPriority() {
		return taskPriority;
	}
	public void setTaskPriority(Priority taskPriority) {
		this.taskPriority = taskPriority;
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
