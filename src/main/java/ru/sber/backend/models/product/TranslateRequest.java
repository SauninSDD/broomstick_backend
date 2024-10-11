package ru.sber.backend.models.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TranslateRequest {
    private String folderId;
    private List<String> texts;
    private String targetLanguageCode;

    public TranslateRequest(List<String> texts) {
        this.folderId = "b1gb2hm3gdhbk25ki2ju";
        this.texts = texts;
        this.targetLanguageCode = "en";
    }
}
