package com.managment.moneyManagmentProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.managment.moneyManagmentProject.imp.UserServicesImplement;
import com.managment.moneyManagmentProject.security.JWTAuthenticationFilter;
import com.managment.moneyManagmentProject.security.JwtAuthEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private JwtAuthEntryPoint authEntryPoint;
	private UserServicesImplement userServices;
	
   @Autowired
   public SecurityConfig(JwtAuthEntryPoint authEntryPoint, UserServicesImplement userServices) {
		super();
		this.authEntryPoint = authEntryPoint;
		this.userServices = userServices;
	}

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(authEntryPoint))
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("moneymgm/auth/**").permitAll()
            .anyRequest().authenticated())
        .httpBasic(AbstractHttpConfigurer::disable);
	http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
    }
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter() {
		return new JWTAuthenticationFilter();
	}
	//daddy
}
