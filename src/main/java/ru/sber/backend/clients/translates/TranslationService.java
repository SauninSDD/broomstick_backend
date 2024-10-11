package ru.sber.backend.clients.translates;

import ru.sber.backend.entities.product.ProductCategory;
import ru.sber.backend.models.product.GetProductResponse;

import java.util.List;

/**
 * Переводит текста
 */
public interface TranslationService {
    List<String> translateTexts(List<String> texts);
    List<ProductCategory> translateCategories(List<ProductCategory> categories);
    List<GetProductResponse> translateProducts(List<GetProductResponse> productsForTranslate);

}
