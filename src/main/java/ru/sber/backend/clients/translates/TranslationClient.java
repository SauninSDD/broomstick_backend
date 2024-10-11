package ru.sber.backend.clients.translates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sber.backend.exceptions.TranslateError;
import ru.sber.backend.models.product.TranslateRequest;
import ru.sber.backend.models.product.TranslateResponse;
import ru.sber.backend.models.product.TranslateTokenRequest;
import ru.sber.backend.models.product.TranslateTokenResponse;

import java.util.Objects;

/**
 * Клиент для взаимодействия с сервисом переводов
 */
@Slf4j
@Service
public class TranslationClient {
    private final CacheManager cacheManager;

    @Autowired
    public TranslationClient(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Запрашивает перевод
     *
     * @param translateRequest сущность с данными для перевода
     * @return список переводов
     */
    @Cacheable("translationCache")
    public TranslateResponse getTranslates(TranslateRequest translateRequest) {
        log.info("Объект для перевода {}", translateRequest);

        String urlTranslate = "https://translate.api.cloud.yandex.net/translate/v2/translate";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            headers.add("Authorization", "Bearer " + getTranslateToken());
            HttpEntity<TranslateRequest> translateEntity = new HttpEntity<>(translateRequest, headers);

            ResponseEntity<TranslateResponse> responseTranslate = new RestTemplate().exchange(
                    urlTranslate,
                    HttpMethod.POST,
                    translateEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            log.info("translateResponse {}", responseTranslate.getBody());
            return responseTranslate.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            throw new TranslateError("Перевод завершился ошибкой");
        }
    }


    /**
     * Запрашивает токен для запроса на перевод
     *
     * @return Токен для запросов на переводы
     */
    @Cacheable(value = "tokenCache", key = "'token'")
    public String getTranslateToken() {
        String urlTokenTranslate = "https://iam.api.cloud.yandex.net/iam/v1/tokens";
        HttpEntity<TranslateTokenRequest> translateTokenEntity = new HttpEntity<>(new TranslateTokenRequest());

        try {
            ResponseEntity<TranslateTokenResponse> responseTranslateToken = new RestTemplate().exchange(
                    urlTokenTranslate,
                    HttpMethod.POST,
                    translateTokenEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            log.info("responseTranslateToken: {}", responseTranslateToken);
            return Objects.requireNonNull(responseTranslateToken.getBody()).getIamToken();
        } catch (Exception e) {
            e.printStackTrace();
            throw new TranslateError("Получение токена завершилось ошибкой");
        }
    }

    @Scheduled(fixedRate = 6 * 60 * 60 * 1000) // 6 часов в миллисекундах
    public void evictTokenCache() {
        log.info("Чистим кеш функции получения токена");
        Cache cache = cacheManager.getCache("tokenCache");
        if (cache != null) {
            cache.evict("token");
        }
    }


}
