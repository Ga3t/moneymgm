package com.managment.moneyManagmentProject.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.managment.moneyManagmentProject.dto.*;
import com.managment.moneyManagmentProject.imp.UserServicesImplement;
import com.managment.moneyManagmentProject.model.Category;
import com.managment.moneyManagmentProject.model.UserEntity;
import com.managment.moneyManagmentProject.repository.UserRepository;
import com.managment.moneyManagmentProject.services.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.managment.moneyManagmentProject.exeptions.UnauthorizedException;
import com.managment.moneyManagmentProject.model.Ledger;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("moneymgm/v1/myledger")
@AllArgsConstructor
public class LedgerController {

	private final CategoryServices categoryServices;
	private final LedgerServices service;
	private final PdfGeneratorService pdfGeneratorService;
	private final UserServicesImplement userServices;
	private final UserRepository userRepository;
	private final GeneralServices geneServices;

	@GetMapping("getAllStatistics/{year}/{month}")
	public ResponseEntity<InfoForMainPageDTO> mainpagedown(@RequestHeader("Authorization") String token,
														   @PathVariable("year") int year,
														   @PathVariable("month") int month){
		String jwt = token.replace("Bearer ", "");
		Long userId = geneServices.getUserIdFromToken(jwt);
		try {
			ResponseEntity response = service.getInfoForMainPageNew(userId, year, month);
			return response;
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("getMonthTransactions/{year}/{month}")
	public ResponseEntity<List<MonthLedgerDTO>> mainStatistic(@RequestHeader("Authorization") String token,
														   @PathVariable("year") int year,
														   @PathVariable("month") int month){
		String jwt = token.replace("Bearer ", "");
		Long userId = geneServices.getUserIdFromToken(jwt);

		List<MonthLedgerDTO> monthLedgerDTOList = service.getInfoForMonthLedger(userId, year, month);
		ResponseEntity response = new ResponseEntity(HttpStatus.OK);
		return new ResponseEntity<>(monthLedgerDTOList, HttpStatus.OK);
	}
	
	@PostMapping("createtransaction")
	public ResponseEntity<String> createLedger(@RequestHeader("Authorization") String token,
	                           @RequestBody CreateLedgerDTO ledgerDTO) {

	    String jwt = token.replace("Bearer ", "");
	    Long userId = geneServices.getUserIdFromToken(jwt);
	    System.out.println("Extracted userId: " + userId);
		Category category = categoryServices.findByName(ledgerDTO.getCategoryName());
		LocalDateTime dateOfTran = ledgerDTO.getDate();
		LocalDateTime currentDateTime = LocalDateTime.now();
		if (dateOfTran.isAfter(currentDateTime)) {
			return new ResponseEntity<>("Transaction date cannot be in the future", HttpStatus.BAD_REQUEST);
		}

		if(category == null){
			return new ResponseEntity<>("Category not found!", HttpStatus.BAD_REQUEST);
		}
		Ledger ledger = new Ledger();
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		ledger.setUser(user);
		ledger.setPrice(ledgerDTO.getPrice());
		ledger.setDate(ledgerDTO.getDate());
		ledger.setShortName(ledgerDTO.getShortName());
		ledger.setDescription(ledgerDTO.getDescription());
		ledger.setCategory(category);
		service.createLedger(ledger);
	    return new ResponseEntity<>("Transaction created!", HttpStatus.OK);
	}


	@GetMapping("transactions")
	public ResponseEntity<LedgerResponse> getAllTransactions(
			@RequestHeader("Authorization") String token,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			TransactionFilterDto filter) {
		Long userId = geneServices.getUserIdFromToken(token.replace("Bearer ", ""));
		if (userId == null) {
			throw new UnauthorizedException("Invalid or expired token");
		}
		Page<Ledger> ledgerPage;
		if (filter != null && isFilterNotEmpty(filter)) {
			ledgerPage = service.getAllTransactionsByUserIdWithCustomFilter(userId, pageNo, pageSize, filter);
		} else {
			ledgerPage = service.getAllTransactionsByUserId(userId, pageNo, pageSize);
		}

		List<InfoLedgerDTO> content = ledgerPage.getContent().stream()
				.map(this::convertToInfoLedgerDTO)
				.collect(Collectors.toList());

		LedgerResponse response = new LedgerResponse();
		response.setContent(content);
		response.setPageNo(ledgerPage.getNumber());
		response.setPageSize(ledgerPage.getSize());
		response.setTotalElements(ledgerPage.getTotalElements());
		response.setTotalPages(ledgerPage.getTotalPages());
		response.setLast(ledgerPage.isLast());

		return ResponseEntity.ok(response);
	}

	private InfoLedgerDTO convertToInfoLedgerDTO(Ledger ledger) {
		InfoLedgerDTO dto = new InfoLedgerDTO();
		dto.setId(ledger.getId());
		dto.setPrice(ledger.getPrice());
		dto.setDescription(ledger.getDescription());
		dto.setShortName(ledger.getShortName());
		dto.setDate(ledger.getDate());
		dto.setCategoryName(ledger.getCategory().getName());
		dto.setCategoryType(ledger.getCategory().getCategoryType().toString());
		return dto;
	}


	@GetMapping(value = "generateRaportPDF", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> generatePDFraport(@RequestHeader("Authorization") String token,
													TransactionFilterDto filter) {
		Long userId = geneServices.getUserIdFromToken(token.replace("Bearer ", ""));
		List<Ledger> ledgers = service.getAllTransactionsByUserIdWithCustomFilterForPDF(userId, filter);
		if (ledgers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		byte[] pdfBytes = pdfGeneratorService.generateLedgerReport(ledgers);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "raport.pdf");

		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}

	private CreateLedgerDTO convertToDTO(Ledger ledger) {
	    CreateLedgerDTO dto = new CreateLedgerDTO();
	    dto.setPrice(ledger.getPrice());
	    dto.setDescription(ledger.getDescription());
	    dto.setShortName(ledger.getShortName());
	    dto.setDate(ledger.getDate());
	    return dto;
	}
	private boolean isFilterNotEmpty(TransactionFilterDto filter) {
	    return filter.startDate() != null ||
	           filter.endDate() != null ||
	           filter.startPrice() != null ||
	           filter.endPrice() != null ||
	           filter.categoryName() != null ||
			   filter.categoryType() != null;
	}
}
