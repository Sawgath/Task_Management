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
/*
 * Entity model for project_tb of TaskManagementDb
 */
@Entity
@Table(name = "project_tb")
public class Project implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long projectId;
	@Column(name = "projectName")
	private String projectName;
 
	@Column(name = "projectDescription")
	private String projectDescription;

	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Column(name = "projectStartTime")
	private Date projectStartTime;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Column(name = "projectEndTime")
	private Date projectEndTime;
	
	public Project() {
	}
 
	public Project(String projectName, String projectDescription,Date projectStartTime,Date projectEndTime) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.projectStartTime=projectStartTime;
		this.projectEndTime=projectEndTime;
	}
	
	public long getProjectId() {
		return projectId;
	}
	
	public void setProjectId(long projectId) {
		this.projectId =  projectId;
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

	public Date getProjectStartTime() {
		return projectStartTime;
	}

	public void setProjectStartTime(Date projectStartTime) {
		this.projectStartTime = projectStartTime;
	}

	public Date getProjectEndTime() {
		return projectEndTime;
	}

	public void setProjectEndTime(Date projectEndTime) {
		this.projectEndTime = projectEndTime;
	}
}
