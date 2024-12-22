package com.managment.moneyManagmentProject.imp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.managment.moneyManagmentProject.dto.TransactionFilterDto;
import com.managment.moneyManagmentProject.model.Ledger;
import com.managment.moneyManagmentProject.model.UserEntity;
import com.managment.moneyManagmentProject.repository.LedgerRepository;
import com.managment.moneyManagmentProject.repository.UserRepository;
import com.managment.moneyManagmentProject.services.LedgerServices;

import lombok.AllArgsConstructor;


@Service
@Primary
@AllArgsConstructor
public class LedgerServicesImplement implements LedgerServices{

	
	private final LedgerRepository repository;
	private final UserServicesImplement userServices;
	@Autowired
    private UserRepository userRepository;
	
	@Override
	public Ledger createLedger(Long userId,Ledger ledger) {

        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        ledger.setUser(user);
        return repository.save(ledger);
	}

	@Override
	public Ledger updateLedger(Ledger ledger) {
		return repository.save(ledger);
	}

	@Override
	public Page<Ledger> getAllTransactionsByUserId(Long userId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Ledger> ledger = repository.findAllByUserId(userId, pageable);
		return ledger;
	}

	@Override
	public Page<Ledger> getAllTransactionsByUserIdAndFilter(Long userId, int pageNo, int pageSize,
			TransactionFilterDto transactionFilter) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Ledger> ledger = repository.findAllByUserIdAndFilter(userId, transactionFilter);
		return ledger;
	}
}
