package ru.sber.backend.models.keycloack;

import lombok.Data;

@Data
public class UpdateResponse {
    String email;
    Attributes attributes;
}
