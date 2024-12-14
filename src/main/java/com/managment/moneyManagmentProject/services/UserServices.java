package com.managment.moneyManagmentProject.services;

import org.springframework.stereotype.Service;

import com.managment.moneyManagmentProject.model.User;


@Service
public interface UserServices {
	User saveUser(User user);
	User findById(Long id);
	User findByUsername(String username);
}
