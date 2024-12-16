package com.managment.moneyManagmentProject.dto;

import lombok.Data;

@Data
public class AuthResponceDto {

    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponceDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
