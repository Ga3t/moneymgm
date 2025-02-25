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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ledger> mainQuery = cb.createQuery(Ledger.class);
        Root<Ledger> ledger = mainQuery.from(Ledger.class);


        List<Predicate> predicates = buildPredicates(cb, ledger, userId, transactionFilter);


        mainQuery.select(ledger).where(predicates.toArray(new Predicate[0]));


        TypedQuery<Ledger> typedQuery = entityManager.createQuery(mainQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());


        List<Ledger> results = typedQuery.getResultList();
        System.out.println("Main query executed. Results fetched: " + results.size());


        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Ledger> countRoot = countQuery.from(Ledger.class);


        countQuery.select(cb.count(countRoot)).where(buildPredicates(cb, countRoot, userId, transactionFilter).toArray(new Predicate[0]));


        Long total = entityManager.createQuery(countQuery).getSingleResult();
        System.out.println("Count query executed. Total results: " + total);


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



    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Ledger> ledger, Long userId, TransactionFilterDto transactionFilter) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Ledger, Category> categoryJoin = ledger.join("category", JoinType.LEFT);


        predicates.add(cb.equal(ledger.get("user").get("id"), userId));


        if (transactionFilter.startDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(ledger.get("date"), transactionFilter.startDate()));
        }
        if (transactionFilter.endDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(ledger.get("date"), transactionFilter.endDate()));
        }


        if (transactionFilter.startPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(ledger.get("price"), transactionFilter.startPrice()));
        }
        if (transactionFilter.endPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(ledger.get("price"), transactionFilter.endPrice()));
        }


        if (transactionFilter.categoryType() != null){
            predicates.add(cb.equal(categoryJoin.get("categoryType"), transactionFilter.categoryType() ));
        }


        if (transactionFilter.categoryName() != null) {
            predicates.add(cb.equal(categoryJoin.get("name"), transactionFilter.categoryName()));
        }

        return predicates;
    }
}



