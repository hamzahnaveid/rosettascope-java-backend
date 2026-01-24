package com.rosetta_scope.java_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rosetta_scope.java_backend.dao.UserDao;
import com.rosetta_scope.java_backend.entity.User;

@RestController
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/user/{email}")
	@ResponseBody
	public User getUserByEmail(@PathVariable String email) {
		return userDao.findByEmail(email).get();
	}
	
	@GetMapping("/user-exists/{email}")
	@ResponseBody
	public boolean userExists(@PathVariable String email) {
		return userDao.userExists(email);
	}
	
	@PostMapping("/user-save")
	public void saveUser(@RequestBody User user) {
		userDao.save(user);
	}
	
	@DeleteMapping("user-delete")
	public void deleteUser(@RequestBody User user) {
		userDao.delete(user);
	}
	
	@DeleteMapping("user-delete/{email}")
	public void deleteUser(@PathVariable String email) {
		userDao.deleteByEmail(email);
	}

}
