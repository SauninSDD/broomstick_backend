package ru.sber.backend.clients.translates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.sber.backend.entities.product.ProductCategory;
import ru.sber.backend.models.product.GetProductResponse;
import ru.sber.backend.models.product.TranslateRequest;
import ru.sber.backend.models.product.TranslateResponse;
import ru.sber.backend.models.product.TranslateResponseAttributes;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class TranslationServiceImp implements TranslationService {
    private final TranslationClient translationClient;

    @Autowired
    public TranslationServiceImp(TranslationClient translationClient) {
        this.translationClient = translationClient;
    }

    @Override
    @Cacheable("translationTexts")
    public List<String> translateTexts(List<String> texts) {
        log.warn("texts {}", texts);
        TranslateResponse translateResponse = translationClient.getTranslates(new TranslateRequest(texts));
        log.warn("Перевод текста {}", translateResponse);
        return translateResponse.getTranslations().stream().map(TranslateResponseAttributes::getText).toList();
    }

    @Override
    @Cacheable("translationCategoriesCache")
    public List<ProductCategory> translateCategories(List<ProductCategory> categories) {
        log.warn("categories {}", categories);
        List<String> categoryNames = categories.stream().map(ProductCategory::getName).toList();

        TranslateResponse translateResponse = translationClient.getTranslates(new TranslateRequest(categoryNames));
        List<String> translates = translateResponse.getTranslations().stream().map(TranslateResponseAttributes::getText).toList();

        log.warn("Перевод текста {}", translateResponse);
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setName(translates.get(i));
        }
        return categories;
    }

    @Override
    @Cacheable("translationProductsCache")
    public List<GetProductResponse> translateProducts(List<GetProductResponse> productsForTranslate) {
        List<String> listOfNames = productsForTranslate.stream().map(GetProductResponse::getProductName).toList();
        List<String> listOfDescriptions = productsForTranslate.stream().map(GetProductResponse::getProductDescription).toList();
        List<String> listForTranslate = Stream.concat(listOfNames.stream(), listOfDescriptions.stream())
                .toList();

        log.info("Массив для перевода {}", listForTranslate);
        TranslateResponse translateResponse = translationClient.getTranslates(new TranslateRequest(listForTranslate));
        log.info("translateResponse {}", translateResponse);
        List<String> translates= translateResponse.getTranslations().stream().map(TranslateResponseAttributes::getText).toList();

        for (int i = 0; i < productsForTranslate.size(); i++) {
            int translateIndex = i * 2;
            productsForTranslate.get(i).setProductName(translates.get(translateIndex));
            productsForTranslate.get(i).setProductDescription(translates.get(translateIndex + 1));
        }
        return productsForTranslate;
    }

}
