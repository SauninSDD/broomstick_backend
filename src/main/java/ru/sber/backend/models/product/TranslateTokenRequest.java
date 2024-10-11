package ru.sber.backend.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TranslateTokenRequest {
    private String yandexPassportOauthToken;

    public TranslateTokenRequest() {
        this.yandexPassportOauthToken = "y0_AgAAAABe_h0HAATuwQAAAAEDfr-YAAAuDo6GbjNH94dvFzDkUOYz027Dnw";
    }
}
