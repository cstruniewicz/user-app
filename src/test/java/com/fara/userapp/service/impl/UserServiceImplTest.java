package com.fara.userapp.service.impl;

import com.fara.userapp.dto.CreateUserDto;
import com.fara.userapp.dto.ResponseUserDto;
import com.fara.userapp.entity.User;
import com.fara.userapp.exception.UserAlreadyExistsException;
import com.fara.userapp.exception.UserDataConstraintViolationException;
import com.fara.userapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumSet;

import static com.fara.userapp.enums.UserRolesEnum.ADMIN;
import static com.fara.userapp.enums.UserRolesEnum.USER;
import static com.fara.userapp.enums.UserStateEnum.ACTIVE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserServiceImpl userService;

	@Test
	void shouldCreateNewUser() {
		User newUser = new User();
		newUser.setId(1);
		CreateUserDto createUserDto = buildCreateUserDto(1);

		when(userRepository.save(any(User.class))).thenReturn(newUser);

		ResponseUserDto userDto = userService.createUser(createUserDto);

		assertTrue(userDto.getId().equals(1));
	}

	@Test
	void shouldNotCreateUserWhenMissingMandatoryUserAttributes() {

		CreateUserDto createUserDto = new CreateUserDto();

		Assertions.assertThrows(UserDataConstraintViolationException.class, () -> userService.createUser(createUserDto));

	}

	@Test
	void shouldNotCreateUserWhenUsernameExists() {

		CreateUserDto createUserDto = buildCreateUserDto(1);

		when(userRepository.existsByUserName(anyString())).thenReturn(true);

		Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(createUserDto));
	}

	CreateUserDto buildCreateUserDto(Integer number) {
		String numberLiteral = number.toString();
		CreateUserDto createUserDto = new CreateUserDto();
		createUserDto.setUserName("username_" + numberLiteral);
		createUserDto.setPassword("usernamepassword" + numberLiteral);
		createUserDto.setName("name_" + numberLiteral);
		createUserDto.setEmail("email." + numberLiteral + "@dot.com");
		createUserDto.setState(ACTIVE);
		createUserDto.setRoleSet(EnumSet.of(USER, ADMIN));
		return createUserDto;
	}
}