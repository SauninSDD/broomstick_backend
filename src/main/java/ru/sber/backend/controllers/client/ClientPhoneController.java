package ru.sber.backend.controllers.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.backend.models.client.AddClientPhoneRequest;
import ru.sber.backend.services.client.ClientTelephoneService;

import java.net.URI;
import java.util.List;

/**
 * Контроллер для обработки запросов к номерам телефонов клиента
 */
@Slf4j
@RestController
@RequestMapping("/client/phones")
public class ClientPhoneController {

    private final ClientTelephoneService clientTelephoneService;

    @Autowired
    public ClientPhoneController(ClientTelephoneService clientTelephoneService) {
        this.clientTelephoneService = clientTelephoneService;
    }

    /**
     * Получает все номера пользователя по id
     *
     * @return список телефонов пользователя
     */
    @PreAuthorize("hasRole('client_user')")
    @GetMapping
    public ResponseEntity<List<String>> getClientPhones() {
        log.info("Получение номеров пользователя");
        List<String> clientPhonesList = clientTelephoneService.getAllClientPhonesByClientId();
        log.info("Список номеров пользователя: {}", clientPhonesList);
        return ResponseEntity.ok()
                .body(clientPhonesList);
    }

    /**
     * Добавляет номер телефона
     *
     * @return результат запроса
     */
    @PostMapping
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<String> addClientPhone(@RequestBody AddClientPhoneRequest addClientPhoneRequest) {
        log.info("Создает номер клиента {}", addClientPhoneRequest.getPhone());
        var isAdd = clientTelephoneService.addClientPhone(addClientPhoneRequest.getPhone());
        if (isAdd) {
            return ResponseEntity.created(URI.create("phones"))
                    .body("Телефон добавлен");
        } else {
            return ResponseEntity.badRequest()
                    .body("Телефон уже привязан к одному из аккаунтов");
        }
    }

    /**
     * Удаляет номер телефона клиента
     *
     * @return результат запроса
     */
    @DeleteMapping
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<String> deleteClientPhone(@RequestParam String phone) {
        log.info("Удаляет номер клиента {}", phone);
        var isDeleted = clientTelephoneService.deleteClientPhone(phone);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
