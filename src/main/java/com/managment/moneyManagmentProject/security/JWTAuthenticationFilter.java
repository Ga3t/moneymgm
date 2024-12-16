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
		if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
			String username= tokenGenerator.getUsernameFromJwt(token);
			UserDetails userDetails = userService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
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
