package ru.sber.backend.repositories.client;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.backend.entities.client.ClientCard;

import java.util.List;
import java.util.Optional;

public interface ClientCardRepository extends JpaRepository<ClientCard, Long> {

    List<ClientCard> findAllClientCardsByIdClient(String idClient);

    boolean existsByClientCardNumber(String clientCardNumber);

    Optional<ClientCard> findClientCardByClientCardNumberAndIdClient(String clientCardNumber, String idClient);

}