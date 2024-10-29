package ru.sber.backend.models.translate;

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
