package com.Jahan.Task_Management.helperModel;


public class UserHelper {
	
	
	public long userId;
	
	public String userName;
 

	public String password;
	

	public String email;
	

	public int role;
	
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

}
