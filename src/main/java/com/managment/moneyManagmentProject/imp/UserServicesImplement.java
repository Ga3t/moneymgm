package com.managment.moneyManagmentProject.imp;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.managment.moneyManagmentProject.model.User;
import com.managment.moneyManagmentProject.repository.UserRepository;
import com.managment.moneyManagmentProject.services.UserServices;

import lombok.AllArgsConstructor;


@Service
@Primary
@AllArgsConstructor
public class UserServicesImplement implements UserServices{
	
	private final UserRepository repository;

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return repository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}

}
