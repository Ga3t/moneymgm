package com.managment.moneyManagmentProject.imp;

import com.managment.moneyManagmentProject.model.Category;
import com.managment.moneyManagmentProject.repository.CategoryRepository;
import com.managment.moneyManagmentProject.services.CategoryServices;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
@AllArgsConstructor
public class CategoryServicesImp implements CategoryServices {
    private CategoryRepository repository;

    @Override
    public Category findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Category findByCategorytype(Enum CategoryType) {
        String type= CategoryType.toString();
        return repository.findByCategoryType(type);
    }
}
