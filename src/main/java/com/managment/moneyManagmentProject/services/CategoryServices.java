package com.managment.moneyManagmentProject.services;


import com.managment.moneyManagmentProject.model.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryServices {
    Category findByName (String name);
    Category findByCategorytype(Enum CategoryType);
}
