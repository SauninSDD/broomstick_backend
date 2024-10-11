package ru.sber.backend.services.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.backend.entities.Logfile;
import ru.sber.backend.entities.client.ClientPhone;
import ru.sber.backend.repositories.LoggingRepository;
import ru.sber.backend.repositories.client.ClientPhoneRepository;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ClientTelephoneServiceImp implements ClientTelephoneService {
    private final ClientService clientService;
    private final ClientPhoneRepository clientPhoneRepository;
    private final LoggingRepository loggingRepository;


    @Autowired
    public ClientTelephoneServiceImp(ClientService clientService, ClientPhoneRepository clientPhoneRepository, LoggingRepository loggingRepository) {
        this.clientService = clientService;
        this.clientPhoneRepository = clientPhoneRepository;
        this.loggingRepository = loggingRepository;
    }

    @Override
    public boolean addClientPhone(String clientPhone) {
        log.info("Добавление телефона");
        loggingRepository.save(new Logfile("Попытка добавления телефона пользователя", clientService.getIdClient()));
        var isExistsPhone = clientPhoneRepository.existsByPhone(clientPhone);
        if (!isExistsPhone) {
            ClientPhone addedPhone = ClientPhone.builder()
                    .phone(clientPhone)
                    .idClient(clientService.getIdClient())
                    .build();

            log.info("Добавление телефона клиента {}", clientPhone);
            loggingRepository.save(new Logfile(String.format("Добавление телефона с номером %s", clientPhone), clientService.getIdClient()));
            clientPhoneRepository.save(addedPhone);
            return true;
        }
        loggingRepository.save(new Logfile(String.format("Неудачное добавление телефона с номером %s", clientPhone), clientService.getIdClient()));
        return false;
    }

    @Override
    public List<String> getAllClientPhonesByClientId() {
        loggingRepository.save(new Logfile("Попытка получения телефонов пользователя", clientService.getIdClient()));
        List<ClientPhone> clientPhones = clientPhoneRepository.findAllClientPhonesByIdClient(clientService.getIdClient());
        loggingRepository.save(new Logfile("Получение телефонов пользователя", clientPhones.stream().map(ClientPhone::getPhone).toList(), clientService.getIdClient()));
        return clientPhones.stream().map(ClientPhone::getPhone).toList();
    }

    @Override
    public boolean deleteClientPhone(String clientPhone) {
        loggingRepository.save(new Logfile("Попытка удаления телефона пользователя", clientService.getIdClient()));

        Optional<ClientPhone> deletedPhone = clientPhoneRepository.findClientPhoneByPhoneAndIdClient(clientPhone, clientService.getIdClient());
        if (deletedPhone.isPresent()) {
            clientPhoneRepository.delete(deletedPhone.get());

            log.info("Удален телефон {}", clientPhone);
            loggingRepository.save(new Logfile(String.format("Удаление телефона с номером %s", clientPhone), clientService.getIdClient()));
            return true;
        }

        loggingRepository.save(new Logfile(String.format("Неудачная попытка удаления телефона с номером %s", clientPhone), clientService.getIdClient()));
        return false;
    }
}
