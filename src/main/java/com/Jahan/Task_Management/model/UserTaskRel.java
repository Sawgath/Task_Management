package com.Jahan.Task_Management.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

import com.Jahan.Task_Management.helperModel.UserTaskRelHelperModel;
/*
 * Entity model for the relation between task and user of TaskManagementDb (fact table)
 */
@Entity
@Table(name = "UserTaskRel")
public class UserTaskRel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userTaskRelId;
	@Column(name = "projectId")
	private long projectId;
	@Column(name = "taskId")
	private long taskId;
	@Column(name = "userId")
	private long userId;
	@Column(name = "taskAccepted")
	private long taskAccepted;
	@Column(name = "taskCompleted")
	private long taskCompleted;
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Column(name = "taskStartTime")
	private Date taskStartTime;
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Column(name = "taskEndTime")
	private Date taskEndTime;
	
	public UserTaskRel()
	{
	
	}
	public UserTaskRel(long userTaskRelId,long projectId,long taskId,long userId,long taskAccepted,long taskCompleted, Date taskStartTime, Date taskEndTime)
	{
		this.projectId = projectId;
		this.taskId = taskId;
		this.userId = userId;
		this.taskAccepted = taskCompleted;
		this.taskStartTime = taskStartTime;
		this.taskEndTime = taskEndTime;	
	}
	public UserTaskRel(UserTaskRelHelperModel aUserTaskRelHelperModel,Date taskStartTime, Date taskEndTime)
	{
		this.userTaskRelId = aUserTaskRelHelperModel.userTaskRelId;
		this.projectId = aUserTaskRelHelperModel.projectId;
		this.taskId = aUserTaskRelHelperModel.taskId;
		this.userId = aUserTaskRelHelperModel.userId;
		this.taskAccepted = aUserTaskRelHelperModel.taskCompleted;
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
