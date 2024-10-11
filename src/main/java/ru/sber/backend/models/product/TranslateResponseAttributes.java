package ru.sber.backend.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateResponseAttributes {
    private String text;
    private String detectedLanguageCode;
}
