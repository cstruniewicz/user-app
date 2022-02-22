package com.fara.userapp.dto;

import com.fara.userapp.entity.User;
import com.fara.userapp.enums.UserRolesEnum;
import com.fara.userapp.enums.UserStateEnum;

import java.util.Set;

public class ResponseUserDto {

	private Integer id;
	private String userName;
	private String name;
	private String email;
	private UserStateEnum state;
	private Set<UserRolesEnum> roleSet;
	private String comment;

	public ResponseUserDto() {
	}

	public ResponseUserDto(User user) {
		this.id = user.getId();
		this.userName = user.getUserName();
		this.name = user.getName();
		this.email = user.getEmail();
		this.state = user.getState();
		this.roleSet = user.getRoleSet();
		this.comment = user.getComment();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserStateEnum getState() {
		return state;
	}

	public void setState(UserStateEnum state) {
		this.state = state;
	}

	public Set<UserRolesEnum> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<UserRolesEnum> roleSet) {
		this.roleSet = roleSet;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
