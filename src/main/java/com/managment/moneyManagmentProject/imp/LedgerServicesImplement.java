package com.managment.moneyManagmentProject.imp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.managment.moneyManagmentProject.model.Ledger;
import com.managment.moneyManagmentProject.repository.LedgerRepository;
import com.managment.moneyManagmentProject.services.LedgerServices;

import lombok.AllArgsConstructor;


@Service
@Primary
@AllArgsConstructor
public class LedgerServicesImplement implements LedgerServices{

	private final LedgerRepository repository;
	
	@Override
	public Ledger createLedger(Ledger ledger) {
		return repository.save(ledger);
	}

	@Override
	public Ledger updateLedger(Ledger ledger) {
		return repository.save(ledger);
	}

	@Override
	public List<Ledger> getAllTransactionsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return repository.findAllByUserId(userId);
	}

//	@Override
//	public List<Ledger> getTransactionsByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
//		// TODO Auto-generated method stub
//		return repository.findByDateTimeBetween(startDate, endDate);
//	}

}
