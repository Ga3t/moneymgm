package com.managment.moneyManagmentProject.services;


import java.time.LocalDateTime;
import java.util.List;

import com.managment.moneyManagmentProject.dto.InfoForMainPageDTO;
import com.managment.moneyManagmentProject.dto.MonthLedgerDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.managment.moneyManagmentProject.dto.TransactionFilterDto;
import com.managment.moneyManagmentProject.model.Ledger;

@Service
public interface LedgerServices {
	Ledger createLedger(Ledger ledger);
	Ledger updateLedger(Ledger ledger);
	Page<Ledger>getAllTransactionsByUserId(Long userId, int pageNo, int pageSeize);
	Page<Ledger> getAllTransactionsByUserIdWithCustomFilter(Long userId, int pageNo, int pageSize,
			TransactionFilterDto transactionFilter);
	public List<Ledger> getAllTransactionsByUserIdWithCustomFilterForPDF(Long userId, TransactionFilterDto transactionFilter);
	public InfoForMainPageDTO getInfoForMainPage(Long userId, int year, int month);
	List<MonthLedgerDTO> getInfoForMonthLedger(Long userId, int year, int month);
}
