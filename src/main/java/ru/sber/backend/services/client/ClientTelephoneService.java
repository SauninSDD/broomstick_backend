package ru.sber.backend.services.client;


import java.util.List;

public interface ClientTelephoneService {
    boolean addClientPhone(String clientPhone);

    List<String> getAllClientPhonesByClientId();

    boolean deleteClientPhone(String clientPhone);
}
