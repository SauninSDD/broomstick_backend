package ru.sber.backend.exceptions;

/**
 * Стоит выбрасывать если номер клиента не найден
 */
public class ClientPhoneNotFound extends RuntimeException {
    public ClientPhoneNotFound() {
    }

    public ClientPhoneNotFound(String message) {
        super(message);
    }
}
