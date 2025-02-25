package com.managment.moneyManagmentProject.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class InfoForMainPageDTO {
    private BigDecimal monthIncome;
    private BigDecimal monthExpense;
    private BigDecimal biggestIncome;
    private BigDecimal biggestExpense;
    private BigDecimal lastTransfer;
    private String categoryType;
    private Integer lastYear;
}
