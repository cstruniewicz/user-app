package com.fara.userapp.service;

import com.fara.userapp.dto.CreateUserDto;
import com.fara.userapp.dto.ResponseUserDto;
import com.fara.userapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	public ResponseUserDto createUser(CreateUserDto createUserDto);

	public User getUser(Integer id);

	public ResponseUserDto updateUser(CreateUserDto createUserDto);

	public void deleteUser(Integer id);

	public Page<ResponseUserDto> searchUsers(String userName, String name, String email, Pageable pageable);

}
