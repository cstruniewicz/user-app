package com.fara.userapp.service.impl;

import com.fara.userapp.dto.CreateUserDto;
import com.fara.userapp.dto.ResponseUserDto;
import com.fara.userapp.entity.User;
import com.fara.userapp.exception.BaseCrudException;
import com.fara.userapp.exception.NoSuchUserException;
import com.fara.userapp.exception.UserAlreadyExistsException;
import com.fara.userapp.exception.UserDataConstraintViolationException;
import com.fara.userapp.repository.UserRepository;
import com.fara.userapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	ModelMapper modelMapper;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.modelMapper = new ModelMapper();
	}

	@Override
	public ResponseUserDto createUser(CreateUserDto createUserDto) throws BaseCrudException{
		validateUserOnCreate(createUserDto);
		createUserDto.setId(null);
		User newUser = new User();
		modelMapper.map(createUserDto, newUser);
		return modelMapper.map(userRepository.save(newUser), ResponseUserDto.class);
	}

	@Override
	public User getUser(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ResponseUserDto updateUser(CreateUserDto createUserDto) throws BaseCrudException{
		User user = validateUserOnUpdate(createUserDto);
		copyUserData(user, createUserDto);

		return modelMapper
				.map(userRepository.findById(user.getId()).orElseThrow(NoSuchUserException::new)
						, ResponseUserDto.class);
	}

	@Override
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public Page<ResponseUserDto> searchUsers(String userName, String name, String email, Pageable pageable) {
		return userRepository.findUsersByUserNameOrNameOrEmail(userName, name, email, pageable)
				.map(ResponseUserDto::new);
	}

	private void validateUserOnCreate(CreateUserDto createUserDto) throws BaseCrudException {
		validateBaseUserConstraints(createUserDto);
		if (userRepository.existsByUserName(createUserDto.getUserName())) {
			throw new UserAlreadyExistsException("User already exists");
		}
	}

	private User validateUserOnUpdate(CreateUserDto updateUserDto) throws BaseCrudException {
		validateBaseUserConstraints(updateUserDto);

		User oldUser = userRepository.findById(updateUserDto.getId())
				.orElseThrow(NoSuchUserException::new);
		User verificationUser = userRepository.findUserByUserName(updateUserDto.getUserName());

		if (verificationUser != null && !verificationUser.getId().equals(oldUser.getId())) {
			throw new UserAlreadyExistsException("Username already exists");
		}

		return oldUser;
	}

	private void validateBaseUserConstraints(CreateUserDto createUserDto) throws BaseCrudException {
		if (createUserDto == null
				|| createUserDto.getUserName() == null
				|| createUserDto.getPassword() == null
				|| createUserDto.getRoleSet() == null
				|| createUserDto.getUserName().isEmpty()
				|| createUserDto.getPassword().isEmpty()
				|| createUserDto.getRoleSet().isEmpty()
				|| createUserDto.getState() == null) {

			throw new UserDataConstraintViolationException("Mandatory user information missing");
		}
	}

	private void copyUserData(User toUser, CreateUserDto fromUser) {
		toUser.setEmail(fromUser.getEmail());
		toUser.setUserName(fromUser.getUserName());
		toUser.setPassword(fromUser.getPassword());
		toUser.setRoleSet(fromUser.getRoleSet());
		toUser.setName(fromUser.getName());
		toUser.setState(fromUser.getState());
		toUser.setComment(fromUser.getComment());
	}

}
