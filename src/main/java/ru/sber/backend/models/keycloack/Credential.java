package ru.sber.backend.models.keycloack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Credential {
    private String type;
    private String value;
}