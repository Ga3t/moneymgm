package com.managment.moneyManagmentProject.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateLedgerDTO {
    private BigDecimal price;
    private String description;
    private String shortName;
    private LocalDateTime date;
    private String categoryName;
}
