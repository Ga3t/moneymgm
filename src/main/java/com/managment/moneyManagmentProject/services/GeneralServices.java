package com.managment.moneyManagmentProject.services;


import org.springframework.stereotype.Service;

@Service
public interface GeneralServices {

    public Long getUserIdFromToken(String token);
}
