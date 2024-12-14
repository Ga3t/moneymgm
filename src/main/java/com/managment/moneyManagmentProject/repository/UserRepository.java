package com.managment.moneyManagmentProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managment.moneyManagmentProject.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	
}
