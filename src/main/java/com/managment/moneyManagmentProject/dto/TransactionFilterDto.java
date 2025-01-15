package com.managment.moneyManagmentProject.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.managment.moneyManagmentProject.model.CategoryType;
import com.managment.moneyManagmentProject.model.TransactionType;

public record TransactionFilterDto(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
    BigDecimal startPrice,
    BigDecimal endPrice,
    String categoryName,
    CategoryType categoryType
) {}
