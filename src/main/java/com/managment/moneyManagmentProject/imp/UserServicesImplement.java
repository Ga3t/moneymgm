package com.managment.moneyManagmentProject.imp;

import java.util.Collections;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.managment.moneyManagmentProject.model.UserEntity;
import com.managment.moneyManagmentProject.repository.UserRepository;
import com.managment.moneyManagmentProject.services.UserServices;

import lombok.AllArgsConstructor;


@Service
@Primary
@AllArgsConstructor
public class UserServicesImplement implements UserServices{
	
	private final UserRepository repository;

	@Override
	public UserEntity saveUser(UserEntity user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}

	@Override
	public UserEntity findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity findByUsername(String username) {
		// TODO Auto-generated method stub
		return repository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = repository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
		return new User(user.getUsername(),
				user.getPassword(), 
				Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
				);
	}

}
