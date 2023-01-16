package com.nitish.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nitish.entity.User;
import com.nitish.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void initRolesAndUsers()
	{
		userService.initRolesAndUser();
	}
	
	@PostMapping("/registernewuser")
	public User registerNewUser(@RequestBody User user)
	{
		return userService.registerNewUser(user);
		
	}
	
	@GetMapping("/foradmin")
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin()
	{
		return "This URL is only accessiable to admin";
	}
	
	@GetMapping("/forUser")
	@PreAuthorize("hasRole('User')")
	public String forUser()
	{
		return "This URL is only accessiable to User";
	}
	

}
