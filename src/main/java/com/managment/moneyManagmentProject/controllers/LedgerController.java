package com.managment.moneyManagmentProject.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.managment.moneyManagmentProject.exeptions.UnauthorizedException;
import com.managment.moneyManagmentProject.model.Ledger;
import com.managment.moneyManagmentProject.model.UserEntity;
import com.managment.moneyManagmentProject.security.JwtGenerator;
import com.managment.moneyManagmentProject.services.LedgerServices;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("moneymgm/v1/myledger")
@AllArgsConstructor
public class LedgerController {
	
	private final LedgerServices service;
	
	@PostMapping("createtransaction")
	public Ledger createLedger(@RequestHeader("Authorization") String token,
	                           @RequestBody Ledger ledger) {
	    String jwt = token.replace("Bearer ", "");
	    Long userId = getUserIdFromToken(jwt);
	    System.out.println("Extracted userId: " + userId);
	    return service.createLedger(userId, ledger); 
	}
	
	
	@GetMapping("alltransactions")
	public List<Ledger> getAllTransactions(@RequestHeader("Authorization") String token, 
			@RequestParam (value="pageNo", defaultValue = "0", required = false) int pageNo, 
			@RequestParam (value="pageSize", defaultValue = "10", required = false) int pageSize) {
	    String jwt = token.replace("Bearer ", "");
	    Long userId = getUserIdFromToken(jwt);
	    if (userId == null) {
            throw new UnauthorizedException("Invalid or expired token");
        }
	    return service.getAllTransactionsByUserId(userId, pageNo, pageSize);
	}

	private Long getUserIdFromToken(String token) {
	    Claims claims = Jwts.parserBuilder()
	                         .setSigningKey(JwtGenerator.getKey()) 
	                         .build()
	                         .parseClaimsJws(token)
	                         .getBody();

	  
	    return claims.get("id", Long.class);
	}
}
