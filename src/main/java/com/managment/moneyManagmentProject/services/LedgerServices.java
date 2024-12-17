package com.managment.moneyManagmentProject.services;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.managment.moneyManagmentProject.model.Ledger;

@Service
public interface LedgerServices {
	Ledger createLedger(Long userId, Ledger ledger);
	Ledger updateLedger(Ledger ledger);
	List<Ledger>getAllTransactionsByUserId(Long userId, int pageNo, int pageSeize);
}
