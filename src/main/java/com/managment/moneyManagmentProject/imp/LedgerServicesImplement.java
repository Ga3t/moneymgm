package com.managment.moneyManagmentProject.imp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.managment.moneyManagmentProject.dto.InfoForMainPageDTO;
import com.managment.moneyManagmentProject.dto.MonthLedgerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.managment.moneyManagmentProject.dto.TransactionFilterDto;
import com.managment.moneyManagmentProject.model.Ledger;
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
	public Ledger createLedger(Ledger ledger) {

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
	public Page<Ledger> getAllTransactionsByUserIdWithCustomFilter(Long userId, int pageNo, int pageSize,
			TransactionFilterDto transactionFilter) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Ledger> ledger = repository.findWithCustomFilter(userId, pageable , transactionFilter);
		return ledger;
	}

	@Override
	public List<Ledger> getAllTransactionsByUserIdWithCustomFilterForPDF(Long userId, TransactionFilterDto transactionFilter) {
		return repository.findWithCustomFilterforPdf(userId, transactionFilter);
	}

	@Override
	public ResponseEntity<InfoForMainPageDTO> getInfoForMainPageNew(Long userId, int year, int month) {
		InfoForMainPageDTO dto = new InfoForMainPageDTO();
		Optional<BigDecimal> monthIncome = repository.sumIncomeForMonth(userId, year, month);
		dto.setMonthIncome(monthIncome.orElse(BigDecimal.ZERO));

		Optional<BigDecimal> monthExpences = repository.sumExpenseForMonth(userId, year, month);
		dto.setMonthExpense(monthExpences.orElse(BigDecimal.ZERO));

		Optional<BigDecimal> biggestIncome = repository.findBiggestIncome(userId, year, month);
		dto.setBiggestIncome(biggestIncome.orElse(BigDecimal.ZERO));

		Optional<BigDecimal> biggestExpense = repository.findBiggestExpense(userId, year, month);
		dto.setBiggestExpense(biggestExpense.orElse(BigDecimal.ZERO));

		Optional<Ledger> lastTransaction = repository.findLastByUser_IdAndYearAndMonthOrderByDateDesc(userId, year, month);
		if (lastTransaction.isPresent()) {
			dto.setLastTransfer(lastTransaction.get().getPrice());
			dto.setCategoryType(lastTransaction.get().getCategory().getCategoryType().toString());
		}
		Optional<Integer> lastYear= repository.findFirstTransactionYearByUserId(userId);
		dto.setLastYear(lastYear.orElse(null));
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<List<MonthLedgerDTO>> getInfoForMonthLedgerNew(Long userId, int year, int month) {
		List<Ledger> ledgers = repository.findAllByUserIdAndYearAndMonth(userId, year, month);
		List<MonthLedgerDTO> dtos =ledgers.stream()
				.map(ledger -> {
					MonthLedgerDTO dto = new MonthLedgerDTO();
					dto.setId(ledger.getId());
					dto.setPrice(ledger.getPrice());
					dto.setShortName(ledger.getShortName());
					dto.setDate(ledger.getDate());
					dto.setCategoryName(ledger.getCategory().getName());
					dto.setCategoryType(ledger.getCategory().getCategoryType());
					return dto;
				})
				.collect(Collectors.toList());
		 return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@Override
	public InfoForMainPageDTO getInfoForMainPage(Long userId, int year, int month) {
		InfoForMainPageDTO dto = new InfoForMainPageDTO();
		Optional<BigDecimal> monthIncome = repository.sumIncomeForMonth(userId, year, month);
		dto.setMonthIncome(monthIncome.orElse(BigDecimal.ZERO));
		Optional<BigDecimal> monthExpences = repository.sumExpenseForMonth(userId, year, month);
		dto.setMonthExpense(monthExpences.orElse(BigDecimal.ZERO));

		Optional<BigDecimal> biggestIncome = repository.findBiggestIncome(userId, year, month);
		dto.setBiggestIncome(biggestIncome.orElse(BigDecimal.ZERO));

		Optional<BigDecimal> biggestExpense = repository.findBiggestExpense(userId, year, month);
		dto.setBiggestExpense(biggestExpense.orElse(BigDecimal.ZERO));

		Optional<Ledger> lastTransaction = repository.findLastByUser_IdAndYearAndMonthOrderByDateDesc(userId, year, month);
		if (lastTransaction.isPresent()) {
			dto.setLastTransfer(lastTransaction.get().getPrice());
			dto.setCategoryType(lastTransaction.get().getCategory().getCategoryType().toString());
		}
		Optional<Integer> lastYear= repository.findFirstTransactionYearByUserId(userId);
		dto.setLastYear(lastYear.orElse(null));


		return dto;

	}

	@Override
	public List<MonthLedgerDTO> getInfoForMonthLedger(Long userId, int year, int month) {
		List<Ledger> ledgers = repository.findAllByUserIdAndYearAndMonth(userId, year, month);
		return ledgers.stream()
				.map(ledger -> {
					MonthLedgerDTO dto = new MonthLedgerDTO();
					dto.setId(ledger.getId());
					dto.setPrice(ledger.getPrice());
					dto.setShortName(ledger.getShortName());
					dto.setDate(ledger.getDate());
					dto.setCategoryName(ledger.getCategory().getName());
					dto.setCategoryType(ledger.getCategory().getCategoryType());
					return dto;
				})
				.collect(Collectors.toList());
	}
}
