package ru.sber.backend.repositories.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.backend.entities.client.ClientPhone;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для взаимодействия с номерами клиента
 */
@Repository
public interface ClientPhoneRepository extends JpaRepository<ClientPhone, Long> {

        List<ClientPhone> findAllClientPhonesByIdClient(String idClient);

        boolean existsByPhone(String phone);

        Optional<ClientPhone> findClientPhoneByPhoneAndIdClient(String phone, String idClient);

}
