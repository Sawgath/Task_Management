package com.Jahan.Task_Management.model;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "task_tb")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long taskId;
	@Column(name = "parentId")
	private long parentId;
	@Column(name = "projectId")
	private long projectId;
	@Column(name = "taskName")
	private String taskName;
	@Column(name = "taskDescription")
	private String taskDescription;
	@Column(name = "taskGoal")
	private String taskGoal;
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Column(name = "taskStartTime")
	private Date taskStartTime;
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Column(name = "taskEndTime")
	private Date taskEndTime;
	
	public Task() 
	{
	}
	public Task( long parentId, long projectId, String taskName,String taskDescription,String taskGoal, Date taskStartTime, Date taskEndTime) {
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
	public Date getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public Date getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
}
