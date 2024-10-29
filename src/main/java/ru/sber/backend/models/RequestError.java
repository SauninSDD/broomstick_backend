package ru.sber.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestError {
    private String title;
    private String text;
}
