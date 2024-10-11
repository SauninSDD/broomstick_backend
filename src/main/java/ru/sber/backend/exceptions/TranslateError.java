package ru.sber.backend.exceptions;

public class TranslateError extends RuntimeException{
    public TranslateError(String message) {
        super(message);
    }

}
