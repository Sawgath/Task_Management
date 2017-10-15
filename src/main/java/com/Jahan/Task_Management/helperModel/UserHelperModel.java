package com.Jahan.Task_Management.helperModel;

import com.Jahan.Task_Management.model.Role;
import com.Jahan.Task_Management.model.User;

//helper model for mapping entity User model

public class UserHelperModel {
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public long userId;
	public String userName;
	public String password;
	public String email;
	public Role role;
	public String active;
	public UserHelperModel() {
	}
	public UserHelperModel(User aUser) {
		this.userId = aUser.getuserId();
		this.userName = aUser.getuserName();
		this.password = aUser.getpassword();
		this.email=aUser.getemail();
		this.role=aUser.getrole();
		this.active=Integer.toString(aUser.getActive());
	}
	public long getuserId() {
		return userId;
	}
	public void setuserId(long userId) {
		this.userId = userId;
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
	public Role getrole() {
		return role;
	}
	public void setrole(Role role) {
		this.role = role;
	}
}
