package com.managment.moneyManagmentProject.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.managment.moneyManagmentProject.dto.LedgerDTO;
import com.managment.moneyManagmentProject.dto.LedgerResponse;
import com.managment.moneyManagmentProject.dto.TransactionFilterDto;
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
	
	
	@GetMapping("transactions")
	public LedgerResponse getAllTransactions(
	        @RequestHeader("Authorization") String token, 
	        @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo, 
	        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, 
	        TransactionFilterDto filter) {
	    String jwt = token.replace("Bearer ", "");
	    Long userId = getUserIdFromToken(jwt);
	    if (userId == null) {
	        throw new UnauthorizedException("Invalid or expired token");
	    }	   
	    Page<Ledger> page = service.getAllTransactionsByUserId(userId, pageNo, pageSize);
	    List<LedgerDTO> content = page.getContent()
	                                  .stream()
	                                  .map(this::convertToDTO)
	                                  .toList();

	    LedgerResponse response = new LedgerResponse();
	    response.setContent(content);
	    response.setPageNo(page.getNumber());
	    response.setPageSize(page.getSize());
	    response.setTotalElements(page.getTotalElements());
	    response.setTotalPages(page.getTotalPages());
	    response.setLast(page.isLast());
	    
	    return response;
	}
	

    @GetMapping("transactionsByfilter")
    public LedgerResponse getTransactionByFilter(@RequestHeader("Authorized") String token, 
    		@RequestParam (value="PageNo", defaultValue = "0", required = false) int pageNo,
    		@RequestParam(value = "PageSize", defaultValue = "10", required = false) int pageSize,
    		TransactionFilterDto transactionFilter
    		)
    {
    	String jwt = token.replace("Bearer ", "");
    	Long userId = getUserIdFromToken(token);
    	if (userId ==null) {
    		throw new UnauthorizedException("Token expired or incorrect");
    	}
    	//Добавить обработку if(startDate==null); if(startPrice==null&endPrice!=null)
    	LedgerResponse response = new LedgerResponse();
    	if(transactionFilter != null) {
    		Page<Ledger> page = service.getAllTransactionsByUserIdAndFilter(userId, pageNo, pageSize, transactionFilter);
        	List<LedgerDTO> content = page.getContent()
        							   .stream()
        							   .map(this::convertToDTO)
        							   .toList();
        	response.setContent(content);
        	response.setPageNo(page.getNumber());
        	response.setPageSize(page.getSize());
        	response.setTotalElements(page.getTotalElements());
        	response.setLast(page.isLast());
    	}
    	else {
    		Page<Ledger> page = service.getAllTransactionsByUserId(userId, pageNo, pageSize);
    		List<LedgerDTO> content = page.getContent()
    							   .stream()
    							   .map(this::convertToDTO)
    							   .toList();
    		response.setContent(content);
        	response.setPageNo(page.getNumber());
        	response.setPageSize(page.getSize());
        	response.setTotalElements(page.getTotalElements());
        	response.setLast(page.isLast());
    	}
    	
    	
    	
    	return response;
    }

	private LedgerDTO convertToDTO(Ledger ledger) {
	    LedgerDTO dto = new LedgerDTO();
	    dto.setId(ledger.getId());
	    dto.setTransactionType(ledger.getTransactionType());
	    dto.setPrice(ledger.getPrice());
	    dto.setDescription(ledger.getDescription());
	    dto.setShortName(ledger.getShortName());
	    dto.setDate(ledger.getDate());
	    dto.setCategory(ledger.getCategory());
	    return dto;
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
