package ru.sber.backend.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateTokenResponse {
    private String iamToken;
    private String expiresAt;
}