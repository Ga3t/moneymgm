package com.managment.moneyManagmentProject.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.managment.moneyManagmentProject.model.UserEntity;


@Service
public interface UserServices  extends UserDetailsService{
	UserEntity saveUser(UserEntity user);
	UserEntity findById(Long id);
	UserEntity findByUsername(String username);
}
