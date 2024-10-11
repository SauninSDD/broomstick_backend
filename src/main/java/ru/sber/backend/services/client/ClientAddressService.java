package ru.sber.backend.services.client;

import ru.sber.backend.entities.client.ClientAddress;

import java.util.List;

public interface ClientAddressService {
    Long addClientAddress(ClientAddress clientAddress);

    ClientAddress getClientAddress(Long clientAddressId);

    List<ClientAddress> getAllClientAddressesByClientId(Long clientId);

    boolean updateClientAddress(ClientAddress clientAddress);

    boolean deleteClientAddress(Long clientAddressId);
}
