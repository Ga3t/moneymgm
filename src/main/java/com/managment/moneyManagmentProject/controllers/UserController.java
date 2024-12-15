package com.managment.moneyManagmentProject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managment.moneyManagmentProject.model.UserEntity;
import com.managment.moneyManagmentProject.services.UserServices;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("moneymgm/v1/user")
@AllArgsConstructor
public class UserController {
	
	private final UserServices service;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome home, buddy";
	}
//	@PostMapping("/register")
//	public UserEntity saveUser(@Valid @RequestBody UserEntity user) {
//	    return service.saveUser(user);
//	}
//	@GetMapping("/{id}")
//	public UserEntity findById(@PathVariable Long id) {
//	    return service.findById(id);
//	}
}
