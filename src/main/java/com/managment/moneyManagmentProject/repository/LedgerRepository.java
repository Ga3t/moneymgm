package com.managment.moneyManagmentProject.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managment.moneyManagmentProject.model.Ledger;

public interface LedgerRepository extends JpaRepository<Ledger, Long>{
	List<Ledger> findByUserId(Long userId);
	List<Ledger> findByCategoryId(Long categoryId);
	List<Ledger> findAllByUserId(Long userId);
	//List<Ledger> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate); 
    //List<Ledger> findByPriceBetween(BigDecimal startPrice, BigDecimal endPrice); 
	//List<Ledger> findByCategory(String categoryName);
}
