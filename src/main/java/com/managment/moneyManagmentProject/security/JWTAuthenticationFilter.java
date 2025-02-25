package com.managment.moneyManagmentProject.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.managment.moneyManagmentProject.imp.UserServicesImplement;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtGenerator tokenGenerator;
	@Autowired
	private UserServicesImplement userService;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    String token = getJWTFromRequest(request);
		System.out.println(request);
		System.out.println("Extracted Token: " + token);

	    if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
	        String username = tokenGenerator.getUsernameFromJwt(token);
	        System.out.println("Username from Token: " + username); 

	        UserDetails userDetails = userService.loadUserByUsername(username);
	        System.out.println("Loaded UserDetails: " + userDetails); 

	        UsernamePasswordAuthenticationToken authenticationToken = 
	            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	    } else {
	        System.out.println("Invalid or missing token");
	    }

	    filterChain.doFilter(request, response);
	}
	
	private String getJWTFromRequest(HttpServletRequest request) {
		String bearedToken=request.getHeader("Authorization");
		if(StringUtils.hasText(bearedToken)&& bearedToken.startsWith("Bearer ")) {
			return bearedToken.substring(7, bearedToken.length());
		}
		return null;
	}

}
