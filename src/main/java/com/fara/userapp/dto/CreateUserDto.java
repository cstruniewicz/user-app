package com.fara.userapp.dto;

import com.fara.userapp.enums.UserRolesEnum;
import com.fara.userapp.enums.UserStateEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

public class CreateUserDto {

	private Integer id;

	@NotEmpty
	@Size(max = 16)
	private String userName;

	@NotEmpty
	@Size(max = 1024)
	private String password;

	@Size(max = 64)
	private String name;

	@Size(max = 320)
	@Email(flags = { Pattern.Flag.CASE_INSENSITIVE })
	private String email;

	@NotNull(message = "state can not be null")
	private UserStateEnum state;

	@NotNull
	@NotEmpty
	private Set<UserRolesEnum> roleSet;

	@Size(max = 255)
	private String comment;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
