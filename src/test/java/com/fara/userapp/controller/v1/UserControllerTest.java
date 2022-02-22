package com.fara.userapp.controller.v1;

import com.fara.userapp.dto.CreateUserDto;
import com.fara.userapp.dto.ResponseUserDto;
import com.fara.userapp.entity.User;
import com.fara.userapp.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.EnumSet;

import static com.fara.userapp.enums.UserRolesEnum.ADMIN;
import static com.fara.userapp.enums.UserRolesEnum.USER;
import static com.fara.userapp.enums.UserStateEnum.ACTIVE;
import static com.fara.userapp.enums.UserStateEnum.INACTIVE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.containsString;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	final ObjectMapper mapper = new ObjectMapper();

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Test
	void shouldNotAcceptIncorrectEmailOnUserCreate() throws Exception {

		mockMvc.perform(post("/v1/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userName\":\"userone\"," +
								"\"password\":\"01234567\"," +
								"\"name\":\"usernameone\"," +
								"\"email\":\"email\"," +
								"\"state\":\"INACTIVE\"," +
								"\"roleSet\":[\"ADMIN\"]," +
								"\"comment\":\"\" }")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().is4xxClientError())
						.andExpect(content().string(containsString("\"type\":\"UserDataConstraintViolationException\"")));
	}

	@Test
	void shouldNotAcceptIncorrectStateOnUserCreate() throws Exception {

				mockMvc.perform(post("/v1/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userName\":\"userone\"," +
								"\"password\":\"01234567\"," +
								"\"name\":\"usernameone\"," +
								"\"email\":\"email@dot.com\"," +
								"\"state\":\"IVE\"," +
								"\"roleSet\":[\"ADMIN\"]," +
								"\"comment\":\"\" }")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().is4xxClientError())
						.andExpect(content()
								.string(containsString("\"type\":\"UserDataConstraintViolationException\"")));
	}
	/* And so on for the rest of user properties ... */


	@Test
	void shouldCreateNewUser() throws Exception {

		CreateUserDto createUserDto = new CreateUserDto();
		createUserDto.setUserName("userone");
		createUserDto.setPassword("01234567");
		createUserDto.setName("usernameone");
		createUserDto.setEmail("email@dot.com");
		createUserDto.setState(ACTIVE);
		createUserDto.setRoleSet(EnumSet.of(USER, ADMIN));
		createUserDto.setComment("Insignificant comment");

		mockMvc.perform(post("/v1/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(createUserDto))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertTrue(userRepository.existsByUserName(createUserDto.getUserName()));
		User user = userRepository.findUserByUserName(createUserDto.getUserName());
		assertEquals(user.getName(), createUserDto.getName());

		//Password should be encrypted now.
		assertNotEquals(user.getPassword(), createUserDto.getPassword());

		assertEquals(user.getEmail(), createUserDto.getEmail());
		assertEquals(user.getState(), createUserDto.getState());
		assertEquals(user.getRoleSet(), createUserDto.getRoleSet());
		assertEquals(user.getComment(), createUserDto.getComment());
	}

	@Test
	void shouldUpdateUser() throws Exception {

		CreateUserDto existingUserDto = new CreateUserDto();
		existingUserDto.setUserName("userone");
		existingUserDto.setPassword("01234567");
		existingUserDto.setName("usernameone");
		existingUserDto.setEmail("email@dot.com");
		existingUserDto.setState(ACTIVE);
		existingUserDto.setRoleSet(EnumSet.of(USER, ADMIN));
		existingUserDto.setComment("Insignificant comment");

		mockMvc.perform(post("/v1/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(existingUserDto))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		User user = userRepository.findUserByUserName(existingUserDto.getUserName());

		CreateUserDto updateUserDto = new CreateUserDto();
		updateUserDto.setId(user.getId());
		updateUserDto.setUserName("userTwo");
		updateUserDto.setPassword("012345679");
		updateUserDto.setName("userNameTwe");
		updateUserDto.setEmail("new.email@dot.com");
		updateUserDto.setState(INACTIVE);
		updateUserDto.setRoleSet(EnumSet.of(ADMIN));
		updateUserDto.setComment("Significant comment");

		mockMvc.perform(put("/v1/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(updateUserDto))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		User updateUser = userRepository.findById(user.getId()).orElse(new User());

		assertEquals(updateUser.getUserName(), updateUserDto.getUserName());
		assertTrue(bCryptPasswordEncoder.matches(updateUserDto.getPassword(), updateUser.getPassword()));
		assertEquals(updateUser.getName(), updateUserDto.getName());
		assertEquals(updateUser.getEmail(), updateUserDto.getEmail());
		assertEquals(updateUser.getState(), updateUserDto.getState());
		assertEquals(updateUser.getRoleSet(), updateUserDto.getRoleSet());
		assertEquals(updateUser.getComment(), updateUser.getComment());
	}

	@Test
	void shouldDeleteUser() throws Exception {

		CreateUserDto createUserDto = new CreateUserDto();
		createUserDto.setUserName("usertwo");
		createUserDto.setPassword("01234567");
		createUserDto.setName("usernameone");
		createUserDto.setEmail("email@dot.com");
		createUserDto.setState(ACTIVE);
		createUserDto.setRoleSet(EnumSet.of(USER, ADMIN));

		ResultActions resultActions = mockMvc.perform(post("/v1/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(createUserDto))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		ResponseUserDto response = mapper.readValue(contentAsString, ResponseUserDto.class);

		CreateUserDto deleteUser = new CreateUserDto();
		deleteUser.setId(response.getId());

		mockMvc.perform(delete("/v1/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(deleteUser))
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());

		assertFalse(userRepository.existsById(deleteUser.getId()));

	}

}

