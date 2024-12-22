package com.managment.moneyManagmentProject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.managment.moneyManagmentProject.dto.TransactionFilterDto;
import com.managment.moneyManagmentProject.model.Ledger;

public interface LedgerRepository extends JpaRepository<Ledger, Long>{
	List<Ledger> findByUserId(Long userId);
	List<Ledger> findByCategoryId(Long categoryId);
	Page<Ledger> findAllByUserId(Long userId, Pageable pageable);
	Page<Ledger> findAllByUserIdAndFilter(Long userId, TransactionFilterDto transactionFilter);
}
