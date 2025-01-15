 package com.managment.moneyManagmentProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managment.moneyManagmentProject.model.UserEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity> findByUsername(String username);
	Boolean existsByUsername(String username);
}
