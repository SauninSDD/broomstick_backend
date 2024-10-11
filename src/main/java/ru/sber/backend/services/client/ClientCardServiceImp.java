package ru.sber.backend.services.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.backend.entities.Logfile;
import ru.sber.backend.entities.client.ClientCard;
import ru.sber.backend.models.client.AddClientCardRequest;
import ru.sber.backend.repositories.LoggingRepository;
import ru.sber.backend.repositories.client.ClientCardRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClientCardServiceImp implements ClientCardService {

    private final ClientService clientService;
    private final ClientCardRepository clientCardRepository;
    private final LoggingRepository loggingRepository;

    @Autowired
    public ClientCardServiceImp(ClientService clientService, ClientCardRepository clientCardRepository, LoggingRepository loggingRepository) {
        this.clientService = clientService;
        this.clientCardRepository = clientCardRepository;
        this.loggingRepository = loggingRepository;
    }

    @Override
    public boolean addClientCard(AddClientCardRequest addClientCardRequest) {
        log.info("Добавление карты");
        loggingRepository.save(new Logfile("Попытка добавления карты пользователя", addClientCardRequest, clientService.getIdClient()));
        var isExistsCard = clientCardRepository.existsByClientCardNumber(addClientCardRequest.getClientCardNumber());
        if (!isExistsCard) {
            ClientCard addedCard = ClientCard.builder()
                    .clientCardNumber(addClientCardRequest.getClientCardNumber())
                    .clientCardCvc(addClientCardRequest.getClientCardCvc())
                    .clientCardExpirationDate(addClientCardRequest.getClientCardExpirationDate())
                    .clientCardOwner(addClientCardRequest.getClientCardOwner())
                    .idClient(clientService.getIdClient())
                    .build();

            log.info("Добавляет карту клиента в бд {}", addedCard);
            loggingRepository.save(new Logfile(String.format("Добавление карты с номером %s", addClientCardRequest.getClientCardNumber()), clientService.getIdClient()));
            clientCardRepository.save(addedCard);
            return true;
        }
        loggingRepository.save(new Logfile(String.format("Неудачная попытка добавления карты с номером %s", addClientCardRequest.getClientCardNumber()), clientService.getIdClient()));
        return false;
    }

    @Override
    public Optional<ClientCard> getClientCard(Long clientCardId) {
        return clientCardRepository.findById(clientCardId);
    }

    @Override
    public List<String> getAllClientCardsByClientId() {
        loggingRepository.save(new Logfile("Попытка получения карт пользователя", clientService.getIdClient()));
        List<ClientCard> clientCards = clientCardRepository.findAllClientCardsByIdClient(clientService.getIdClient());
        loggingRepository.save(new Logfile("Получение карт пользователя", clientCards.stream().map(ClientCard::getClientCardNumber).toList(), clientService.getIdClient()));
        return clientCards.stream().map(ClientCard::getClientCardNumber).toList();
    }

    @Override
    public boolean deleteClientCard(String clientCardNumber) {
        loggingRepository.save(new Logfile("Попытка удаления карты пользователя", clientService.getIdClient()));
        Optional<ClientCard> deletedCard = clientCardRepository.findClientCardByClientCardNumberAndIdClient(clientCardNumber, clientService.getIdClient());
        if (deletedCard.isPresent()) {
            clientCardRepository.delete(deletedCard.get());
            loggingRepository.save(new Logfile(String.format("Удаление карты с номером %s", clientCardNumber), clientService.getIdClient()));
            log.info("Удалена карта {}", clientCardNumber);
            return true;
        }
        loggingRepository.save(new Logfile(String.format("Неудачная попытка удаления карты с номером %s", clientCardNumber), clientService.getIdClient()));
        return false;
    }
}
