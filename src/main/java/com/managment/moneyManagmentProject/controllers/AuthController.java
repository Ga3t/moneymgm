package com.managment.moneyManagmentProject.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managment.moneyManagmentProject.dto.LoginDto;
import com.managment.moneyManagmentProject.dto.RegisterDto;
import com.managment.moneyManagmentProject.model.Role;
import com.managment.moneyManagmentProject.model.UserEntity;
import com.managment.moneyManagmentProject.repository.UserRepository;

@RestController
@RequestMapping("moneymgm/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;
	private UserRepository repository;
	private PasswordEncoder encoder;
	
	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserRepository repository,
			PasswordEncoder encoder) {
		this.authenticationManager = authenticationManager;
		this.repository = repository;
		this.encoder = encoder;
	}
	
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    return new ResponseEntity<>("User enter", HttpStatus.OK);    
	}
	
	@PostMapping("register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		if(repository.existsByUsername(registerDto.getUsername())) {
			return new ResponseEntity<>("Username allready taken!", HttpStatus.BAD_REQUEST);
		}
		UserEntity user =new UserEntity();
		user.setUsername(registerDto.getUsername());
		user.setPassword(encoder.encode((registerDto.getPassword())));
		user.setRole(Role.USER);
		repository.save(user);
		
		return new ResponseEntity<>("User reistred seccessfuly!", HttpStatus.OK);
	}
	
}
