package com.managment.moneyManagmentProject.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="CATEGORY")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME", nullable = false)
	private String name;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name = "CATEGORY_TYPE", nullable = false)
	CategoryType categoryType;
	
	
	@OneToMany(mappedBy = "category", cascade=CascadeType.ALL, orphanRemoval = true )
	@JsonIgnore
	private List<Ledger> ledger;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public CategoryType getCategoryType() {
		return categoryType;
	}


	public void setCategoryType(CategoryType categoryType) {
		this.categoryType = categoryType;
	}


	public List<Ledger> getLedger() {
		return ledger;
	}


	public void setLedger(List<Ledger> ledger) {
		this.ledger = ledger;
	}
	
}
