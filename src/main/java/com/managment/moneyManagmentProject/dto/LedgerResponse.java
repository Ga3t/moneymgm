package com.managment.moneyManagmentProject.dto;

import java.util.List;

import lombok.Data;

@Data
public class LedgerResponse {
	private List<LedgerDTO> content;
	
	private int pageNo;
	private int pageSize;
	private Long totalElements;
	private int totalPages;
	private boolean last;
}
