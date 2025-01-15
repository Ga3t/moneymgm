package com.managment.moneyManagmentProject.dto;


import com.managment.moneyManagmentProject.model.CategoryType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MonthLedgerDTO {
    private Long id;
    private BigDecimal price;
    private String shortName;
    private LocalDateTime date;
    private String categoryName;
    private CategoryType categoryType;
}
