package com.managment.moneyManagmentProject.services;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.managment.moneyManagmentProject.model.Ledger;

@Service
public interface LedgerServices {
	Ledger createLedger(Ledger ledger);
	Ledger updateLedger(Ledger ledger);
	List<Ledger>getAllTransactionsByUserId(Long userId);
	//List<Ledger>getTransactionsByPeriod(LocalDateTime startDate, LocalDateTime endDate);
}
