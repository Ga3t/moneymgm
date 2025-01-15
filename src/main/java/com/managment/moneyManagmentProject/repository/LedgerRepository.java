package com.managment.moneyManagmentProject.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.managment.moneyManagmentProject.dto.TransactionFilterDto;
import com.managment.moneyManagmentProject.model.Ledger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long>, FilterLedgerRepository{
	List<Ledger> findByUserId(Long userId);
	List<Ledger> findByCategoryId(Long categoryId);
	Page<Ledger> findAllByUserId(Long userId, Pageable pageable);
	@Query("SELECT SUM(l.price) FROM Ledger l JOIN l.category c WHERE l.user.id = :userId AND c.categoryType = 'INCOME' AND EXTRACT(YEAR FROM l.date) = :year AND EXTRACT(MONTH FROM l.date) = :month")
	Optional<BigDecimal> sumIncomeForMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

	@Query("SELECT SUM(l.price) FROM Ledger l JOIN l.category c WHERE l.user.id = :userId AND c.categoryType = 'EXPENSES' AND EXTRACT(YEAR FROM l.date) = :year AND EXTRACT(MONTH FROM l.date) = :month")
	Optional<BigDecimal> sumExpenseForMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

	@Query("SELECT MAX(l.price) FROM Ledger l JOIN l.category c WHERE l.user.id = :userId AND c.categoryType = 'INCOME' AND EXTRACT(YEAR FROM l.date) = :year AND EXTRACT(MONTH FROM l.date) = :month")
	Optional<BigDecimal> findBiggestIncome(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

	@Query("SELECT MAX(l.price) FROM Ledger l JOIN l.category c WHERE l.user.id = :userId AND c.categoryType = 'EXPENSES' AND EXTRACT(YEAR FROM l.date) = :year AND EXTRACT(MONTH FROM l.date) = :month")
	Optional<BigDecimal> findBiggestExpense(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

	@Query("SELECT MIN(YEAR(l.date)) FROM Ledger l WHERE l.user.id = :userId")
	Optional<Integer> findFirstTransactionYearByUserId(@Param("userId") Long userId);

	@Query("SELECT l FROM Ledger l WHERE l.user.id = :userId AND EXTRACT(YEAR FROM l.date) = :year AND EXTRACT(MONTH FROM l.date) = :month ORDER BY l.date DESC LIMIT 1")
	Optional<Ledger> findLastByUser_IdAndYearAndMonthOrderByDateDesc(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

	@Query("SELECT l FROM Ledger l JOIN l.category c WHERE l.user.id = :userId AND EXTRACT(YEAR FROM l.date) = :year AND EXTRACT(MONTH FROM l.date) = :month")
	List<Ledger> findAllByUserIdAndYearAndMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);
}
