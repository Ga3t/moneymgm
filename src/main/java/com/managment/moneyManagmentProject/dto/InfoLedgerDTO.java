package com.managment.moneyManagmentProject.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InfoLedgerDTO {
    private Long id;
    private BigDecimal price;
    private String description;
    private String shortName;
    private LocalDateTime date;
    private String categoryName;
    private String categoryType;
}
