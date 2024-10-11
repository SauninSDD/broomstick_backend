package ru.sber.backend.services.client;

import ru.sber.backend.entities.client.ClientCard;
import ru.sber.backend.models.client.AddClientCardRequest;

import java.util.List;
import java.util.Optional;

public interface ClientCardService {
    boolean addClientCard(AddClientCardRequest addClientCardRequest);

    Optional<ClientCard> getClientCard(Long clientCardId);

    List<String> getAllClientCardsByClientId();

    boolean deleteClientCard(String clientCardNumber);

}
