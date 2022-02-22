package com.fara.userapp.entity;

import com.fara.userapp.converter.PasswordEncryptionConverter;
import com.fara.userapp.enums.UserRolesEnum;
import com.fara.userapp.enums.UserStateEnum;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USERS")
public class User extends OptimisticLockedEntity{

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	@Column(name = "USER_NAME", nullable = false, unique=true, length = 16)
	private String userName;

	@Convert(converter = PasswordEncryptionConverter.class)
	@Column(name = "PASSWORD", nullable = false, length = 1024)
	private String password;

	@Column(name = "NAME", length = 64)
	private String name;

	@Column(name = "EMAIL", length = 320)
	private String email;

	@Column(name = "STATE", nullable = false)
	private UserStateEnum state;

	@ElementCollection(targetClass = UserRolesEnum.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "ROLE_NAME")
	private Set<UserRolesEnum> roleSet;

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
