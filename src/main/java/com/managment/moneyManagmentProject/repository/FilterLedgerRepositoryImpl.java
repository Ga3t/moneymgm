package com.managment.moneyManagmentProject.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.managment.moneyManagmentProject.dto.TransactionFilterDto;
import com.managment.moneyManagmentProject.model.Category;
import com.managment.moneyManagmentProject.model.Ledger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;

@RequiredArgsConstructor
public class FilterLedgerRepositoryImpl implements FilterLedgerRepository {
    private final EntityManager entityManager;

    @Override
    public Page<Ledger> findWithCustomFilter(Long userId, Pageable pageable, TransactionFilterDto transactionFilter) {
        System.out.println("Method findWithCustomFilter started");

        // Create CriteriaBuilder and main CriteriaQuery for fetching data
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ledger> mainQuery = cb.createQuery(Ledger.class);
        Root<Ledger> ledger = mainQuery.from(Ledger.class);

        // List to hold predicates
        List<Predicate> predicates = buildPredicates(cb, ledger, userId, transactionFilter);

        // Apply predicates to the main query
        mainQuery.select(ledger).where(predicates.toArray(new Predicate[0]));

        // Add pagination
        TypedQuery<Ledger> typedQuery = entityManager.createQuery(mainQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Execute the main query
        List<Ledger> results = typedQuery.getResultList();
        System.out.println("Main query executed. Results fetched: " + results.size());

        // Create count query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Ledger> countRoot = countQuery.from(Ledger.class);

        // Apply the same predicates to the count query
        countQuery.select(cb.count(countRoot)).where(buildPredicates(cb, countRoot, userId, transactionFilter).toArray(new Predicate[0]));

        // Execute the count query
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        System.out.println("Count query executed. Total results: " + total);

        // Return results as a Page
        System.out.println("Method findWithCustomFilter finished");
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public List<Ledger> findWithCustomFilterforPdf(Long userId, TransactionFilterDto transactionFilter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ledger> mainQuery = cb.createQuery(Ledger.class);
        Root<Ledger> ledger = mainQuery.from(Ledger.class);

        List<Predicate> predicates = buildPredicates(cb, ledger, userId, transactionFilter);

        mainQuery.select(ledger).where(predicates.toArray(new Predicate[0]));

        TypedQuery<Ledger> typedQuery = entityManager.createQuery(mainQuery);

        return typedQuery.getResultList();
    }


    /**
     * Builds a list of predicates based on the filters.
     */
    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Ledger> ledger, Long userId, TransactionFilterDto transactionFilter) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Ledger, Category> categoryJoin = ledger.join("category", JoinType.LEFT);

        // User ID filter
        predicates.add(cb.equal(ledger.get("user").get("id"), userId));

        // Date filters
        if (transactionFilter.startDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(ledger.get("date"), transactionFilter.startDate()));
        }
        if (transactionFilter.endDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(ledger.get("date"), transactionFilter.endDate()));
        }

        // Price filters
        if (transactionFilter.startPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(ledger.get("price"), transactionFilter.startPrice()));
        }
        if (transactionFilter.endPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(ledger.get("price"), transactionFilter.endPrice()));
        }


        if (transactionFilter.categoryType() != null){
            predicates.add(cb.equal(categoryJoin.get("categoryType"), transactionFilter.categoryType() ));
        }

        // CategoryServices name filter
        if (transactionFilter.categoryName() != null) {
            predicates.add(cb.equal(categoryJoin.get("name"), transactionFilter.categoryName()));
        }

        return predicates;
    }
}



