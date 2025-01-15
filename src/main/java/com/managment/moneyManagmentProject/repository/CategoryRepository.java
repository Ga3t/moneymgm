package com.managment.moneyManagmentProject.repository;

import com.managment.moneyManagmentProject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
     Category findByName(String name);
     Category findByCategoryType(String type);
}
