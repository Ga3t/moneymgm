package com.managment.moneyManagmentProject.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.managment.moneyManagmentProject.model.Ledger;
import com.managment.moneyManagmentProject.model.User;
import com.managment.moneyManagmentProject.services.LedgerServices;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("moneymgm/v1/myledger")
@AllArgsConstructor
public class LedgerController {
	
	private final LedgerServices service;
	
	@PostMapping("createtransaction")
	public Ledger createLedger(@RequestBody Ledger ledger) {
		return service.createLedger(ledger);
	}
	
//	@GetMapping("periodfilter")
//	public List<Ledger> getTransacByPeriod(
//			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate)
//	{
//		return service.getTransactionsByPeriod(startDate, endDate);
//	}
	
	@GetMapping("alltransactions/{userId}")
	public List<Ledger> getAllTransactions(@PathVariable Long userId){
		return service.getAllTransactionsByUserId(userId);
	}
	
}
