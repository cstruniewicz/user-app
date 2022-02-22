package com.fara.userapp.controller.v1;

import com.fara.userapp.dto.CreateUserDto;
import com.fara.userapp.dto.ResponseUserDto;
import com.fara.userapp.exception.UserDataConstraintViolationException;
import com.fara.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/user")
@Validated
public class UserController {

	UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseUserDto createUser(@Valid @RequestBody CreateUserDto createUserDto) {
		 return userService.createUser(createUserDto);
	}

	@PutMapping
	public ResponseUserDto updateUser(@Valid @RequestBody CreateUserDto updateUserDto) {
		if (updateUserDto.getId() == null) {
			throw new UserDataConstraintViolationException("User ID can not be null or empty on update request");
		}
		return userService.updateUser(updateUserDto);
	}

	@GetMapping("/all")
	public Page<ResponseUserDto> getUsers(@RequestParam(value="username", required=false) String userName
			, @RequestParam(required=false) String name, @RequestParam(required=false) String email, Pageable pageable) {
		return userService.searchUsers(userName, name, email, pageable);
	}

	@DeleteMapping
	public ResponseEntity<Integer> deleteUser(@RequestBody CreateUserDto createUserDto) {
		if (createUserDto != null) {
			userService.deleteUser(createUserDto.getId());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
