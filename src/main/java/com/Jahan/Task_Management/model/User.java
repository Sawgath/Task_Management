package com.Jahan.Task_Management.model;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.Jahan.Task_Management.helperModel.UserHelperModel;

//Entity model for user_tb of TaskManagementDb
@Entity
@Table(name = "user_tb")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	@Column(name = "userName")
	@NotEmpty(message = "*Please provide your name")
	private String userName;
	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	private String password;
	@Column(name = "email")
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	@Column(name = "active")
	private int active;
	@Column(name = "reset_token")
	private String resetToken;
	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public User() {
	}
 
	public Role getrole() {
		return role;
	}

	public void setrole(Role role) {
		this.role = role;
	}

	public User(String userName, String password,String email,Role role) {
		this.userName = userName;
		this.password = password;
		this.email=email;
		this.role=role;
	}
	
	public User(UserHelperModel aUser) {
		this.userName = aUser.userName;
		this.password = aUser.password;
		this.email=aUser.email;
		this.role=aUser.role;
		this.active=Integer.parseInt(aUser.active);
	}
	
	public long setuserId() {
		return userId;
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
	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	
	@Override
	public String toString() {
		return String.format("User[userName='%s', email='%s']",userName, email);
	}
	
}
