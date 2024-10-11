package ru.sber.backend.controllers.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.backend.models.client.AddClientCardRequest;
import ru.sber.backend.services.client.ClientCardService;

import java.net.URI;
import java.util.List;

/**
 * Контроллер для обработки запросов к банковским картам клиента
 */
@Slf4j
@RestController
@RequestMapping("/client/cards")
public class ClientCardController {

    private final ClientCardService clientCardService;

    @Autowired
    public ClientCardController(ClientCardService clientCardService) {
        this.clientCardService = clientCardService;
    }

    /**
     * Получает все карты пользователя по id
     *
     * @return список карт пользователя
     */
    @PreAuthorize("hasRole('client_user')")
    @GetMapping
    public ResponseEntity<List<String>> getClientCards() {
        log.info("Получение карт пользователя");
        List<String> clientCardsList = clientCardService.getAllClientCardsByClientId();
        log.info("Список карт пользователя: {}", clientCardsList);
        return ResponseEntity.ok()
                .body(clientCardsList);
    }

    /**
     * Добавляет карту
     *
     * @return результат запроса
     */
    @PreAuthorize("hasRole('client_user')")
    @PostMapping
    public ResponseEntity<String> addClientCard(@RequestBody AddClientCardRequest addClientCardRequest) {
        log.info("Добавляет карту клиента {}", addClientCardRequest);
        var isAdd = clientCardService.addClientCard(addClientCardRequest);
        if (isAdd) {
            return ResponseEntity.created(URI.create("cards"))
                    .body("Карта добавлена");
        } else {
            return ResponseEntity.badRequest()
                    .body("Карта уже привязана к одному из аккаунтов");
        }
    }

    /**
     * Удаляет карту
     *
     * @return результат запроса
     */
    @PreAuthorize("hasRole('client_user')")
    @DeleteMapping
    public ResponseEntity<String> deleteClientCard(@RequestParam String cardNumber) {
        log.info("Удаляет карту клиента  {}", cardNumber);
        var isDeleted = clientCardService.deleteClientCard(cardNumber);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
