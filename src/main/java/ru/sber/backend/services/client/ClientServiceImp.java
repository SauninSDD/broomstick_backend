package ru.sber.backend.services.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.sber.backend.services.JwtService;

@Service
@Slf4j
public class ClientServiceImp implements ClientService {

    private final JwtService jwtService;

    @Autowired
    public ClientServiceImp(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String getIdClient() {
        Jwt jwt = jwtService.getJwtSecurityContext();
//        Long clientId = Long.parseLong(jwtService.getSubClaim(jwt), 16); //удалить тире и с охранить либо в 16-ой либо перевети в 10-ую и сохранить
//        log.info("Парс id клиента к виду: {}", clientId);
        return jwtService.getSubClaim(jwt);
    }

}
