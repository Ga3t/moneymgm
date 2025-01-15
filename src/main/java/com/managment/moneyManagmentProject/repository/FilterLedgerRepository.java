package com.managment.moneyManagmentProject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.managment.moneyManagmentProject.dto.TransactionFilterDto;
import com.managment.moneyManagmentProject.model.Ledger;

import java.util.List;

public interface FilterLedgerRepository {
	
	Page<Ledger> findWithCustomFilter(Long userId, Pageable pageable, TransactionFilterDto transactionFilter);
	List<Ledger> findWithCustomFilterforPdf(Long userId, TransactionFilterDto transactionFilter);

}
