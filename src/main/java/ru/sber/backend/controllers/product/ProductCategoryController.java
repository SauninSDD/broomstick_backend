package ru.sber.backend.controllers.product;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.backend.clients.translates.TranslationService;
import ru.sber.backend.entities.product.ProductCategory;
import ru.sber.backend.services.product.ProductCategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    private final TranslationService translationService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService, TranslationService translationService) {
        this.productCategoryService = productCategoryService;
        this.translationService = translationService;
    }

    /**
     * Получает категории (без парент id)
     *
     * @return List<String>
     */
    @GetMapping("/getCategories")
    public ResponseEntity<List<ProductCategory>> getCategories(@RequestParam @NotBlank String language) {
        log.info("Получение категорий");
        List<ProductCategory> categories = productCategoryService.getCategories();
        if (language.equals("en") && !categories.isEmpty()) {
            categories = translationService.translateCategories(categories);
            log.info("Переводы категорий: {}", categories);
        }
        log.info("список категорий: {}", categories);
        return ResponseEntity.ok()
                .body(categories);
    }

    /**
     * Получает список подкатгорий (без вложенных подкатегорий) по categoryId
     *
     * @return List<String>
     */
    @GetMapping("/getSubCategories")
    public ResponseEntity<List<String>> getSubCategories(@RequestParam Long categoryId) {
        log.info("Получение подкатегорий по categoryId");
        List<String> subCategories = productCategoryService.getSubCategories(categoryId);
        log.info("список подкатегорий: {}", subCategories);
        return ResponseEntity.ok()
                .body(subCategories);
    }

    /**
     * Получает список вложенных подкатгорий по categoryId
     *
     * @return List<String>
     */
    @GetMapping("/getNestedSubCategories")
    public ResponseEntity<List<String>> getNestedSubCategories(@RequestParam Long categoryId) {
            log.info("Получение подкатегорий (рекурсия) по categoryId");
            List<String> subCategories = productCategoryService.getNestedSubCategories(categoryId);
            log.info("список подкатегорий: {}", subCategories);
            return ResponseEntity.ok()
                    .body(subCategories);
        }

}

