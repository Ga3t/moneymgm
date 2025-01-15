package com.managment.moneyManagmentProject.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.managment.moneyManagmentProject.model.CategoryType;

public record GenerateRaportDTO(
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime currentDate,
		int month,
		int year,
		CategoryType categoryType) {
}
