package com.Jahan.Task_Management.model;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Entity model for user_tb of TaskManagementDb
@Entity
@Table(name = "user_tb")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
 
	@Column(name = "userName")
	private String userName;
 
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "role")
	private int role;
	
	
	public User() {
	}
 
	public User(String userName, String password,String email,int role) {
		this.userName = userName;
		this.password = password;
		this.email=email;
		this.role=role;
	}
	
	public long getuserId() {
		return userId;
	}
 
	public String getuserName() {
		return userName;
	}
	
	public void setuserName(String userName) {
		this.userName = userName;
	}
	
	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}
	
	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}
	
	
	public int getrole() {
		return role;
	}

	public void setrole(int role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return String.format("User[userName='%s', email='%s']",userName, email);
	}

}
