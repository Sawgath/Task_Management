package com.Jahan.Task_Management.helperModel;
import com.Jahan.Task_Management.model.User;

public class PasswordResetHelperModel {

	public User aUser;
	public String oldPassword="";
	
	public String newPassword="";
	
	public String resetToken="";
	
	public String getResetToken() {
		return resetToken;
	}


	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}


	public PasswordResetHelperModel() {
		
	}
	
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public User getaUser() {
		return aUser;
	}
	public void setaUser(User aUser) {
		this.aUser = aUser;
	}
}
