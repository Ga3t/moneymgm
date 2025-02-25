package com.managment.moneyManagmentProject.imp;


import com.managment.moneyManagmentProject.security.JwtGenerator;
import com.managment.moneyManagmentProject.services.GeneralServices;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary

public class GeneralServicesImplement implements GeneralServices {
    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JwtGenerator.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("id", Long.class);
    }
}
