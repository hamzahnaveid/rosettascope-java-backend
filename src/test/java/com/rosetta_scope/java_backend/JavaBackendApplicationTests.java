package com.rosetta_scope.java_backend;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rosetta_scope.java_backend.dao.UserDao;
import com.rosetta_scope.java_backend.entity.User;

@SpringBootTest
class JavaBackendApplicationTests {
	
	@Autowired
	private UserDao userDao;

	@Test
	void addUserTest() {
		User user = new User("test@gmail.com", "1", "Beginner", "es-EN", 21, 7);
		userDao.save(user);
	}
	
//	@ParameterizedTest
//	@ValueSource(strings = {"test@gmail.com"})
	void getUserByEmail(String email) {
		Optional<User> user = userDao.findByEmail(email);
		User u = user.get();
		System.out.println(u.getTargetLanguage());
	}

}
