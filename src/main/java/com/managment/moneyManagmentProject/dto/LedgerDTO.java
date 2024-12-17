package com.managment.moneyManagmentProject.dto;

import com.managment.moneyManagmentProject.model.Category;
import com.managment.moneyManagmentProject.model.TransactionType;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LedgerDTO {
    private Long id;
    private TransactionType transactionType;
    private BigDecimal price;
    private String description;
    private String shortName;
    private LocalDateTime date;
    private Category category; 
}
