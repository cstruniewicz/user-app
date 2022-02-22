package com.fara.userapp.converter;

import com.fara.userapp.entity.User;
import com.fara.userapp.enums.UserStateEnum;
import com.fara.userapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.EnumSet;

import static com.fara.userapp.enums.UserRolesEnum.ADMIN;
import static com.fara.userapp.enums.UserRolesEnum.USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PasswordEncryptionConverterTest {

	@Autowired
	private UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Test
	void passwordShouldBeEncryptedWhenPersisted() {
		User user = new User();
		user.setUserName("username");
		user.setPassword("password");
		user.setState(UserStateEnum.ACTIVE);
		user.setRoleSet(EnumSet.of(USER, ADMIN));

		user = userRepository.save(user);
		User fetchedUser = userRepository.findById(user.getId()).orElse(new User());

		assertNotEquals("password", fetchedUser.getPassword());
		assertTrue(bCryptPasswordEncoder.matches("password", fetchedUser.getPassword()));

	}

}