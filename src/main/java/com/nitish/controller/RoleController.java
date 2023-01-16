package com.nitish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nitish.entity.Role;
import com.nitish.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/createnewrole")
	public Role createNewRole(@RequestBody Role role)
	{
		return roleService.createNewRole(role);
		
	}
	
	

}
