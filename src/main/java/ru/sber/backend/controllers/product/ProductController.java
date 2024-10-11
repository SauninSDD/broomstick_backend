package ru.sber.backend.controllers.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.sber.backend.clients.translates.TranslationService;
import ru.sber.backend.exceptions.ProductNotFound;
import ru.sber.backend.models.product.GetProductResponse;
import ru.sber.backend.services.product.ProductService;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final TranslationService translationService;

    @Autowired
    public ProductController(ProductService productService, TranslationService translationService) {
        this.productService = productService;
        this.translationService = translationService;
    }

    /**
     * Получает продукт по артиклю
     *
     * @return продукт по артиклю
     */
    @GetMapping("/searchByArticle/{productArticle}")
    public ResponseEntity<GetProductResponse> getProductByArticle(@PathVariable int productArticle) {
        try {
            log.info("Получение продукта по артиклю");
            GetProductResponse productByArticle = productService.getProductByArticle(productArticle);
            log.info("продукт по артиклю: {}", productByArticle);
            return ResponseEntity.ok()
                    .body(productByArticle);
        } catch (ProductNotFound productNotFound) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получает нужную страницу продуктов с заданным размером
     *
     * @return список продуктов
     */
    @GetMapping("/searchByCategory")
    public ResponseEntity<List<GetProductResponse>> getListProduct(@RequestParam int page, @RequestParam int size, @RequestParam Long categoryId, @RequestParam String language) {
        Page<GetProductResponse> pageProduct = productService.getProductsByCategoryId(page, size, categoryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-pages", String.valueOf(pageProduct.getTotalPages()));
        List<GetProductResponse> listProducts = pageProduct.getContent();
        if (language.equals("en") && !listProducts.isEmpty()) {
            listProducts = translationService.translateProducts(listProducts);
            log.info("Переводы: {}", listProducts);
        }
        return ResponseEntity.ok()
                .headers(headers)
                .body(listProducts);
    }

    /**
     * Получает список продуктов по названию
     *
     * @return список продуктов с подходящим названием
     */
    @GetMapping("/searchByName")
    public ResponseEntity<List<GetProductResponse>> getProductByName(@RequestParam int page, @RequestParam int size, @RequestParam String name) {
        log.info("Получение продуктов по названию");
        Page<GetProductResponse> productsByName = productService.getProductsByName(page, size, name);
        log.info("продукты по названию: {}", productsByName);
        log.info("Суммарное кол-во страниц: {}", productsByName.getTotalPages());
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-pages", String.valueOf(productsByName.getTotalPages()));
        return ResponseEntity.ok()
                .headers(headers)
                .body(productsByName.getContent());
    }

    /**
     * Получает список продуктов по интервалу цены и категории
     *
     * @return список продуктов
     */
    @GetMapping("/searchByPriceRangeAndCategory")
    public ResponseEntity<List<GetProductResponse>> getProductByPriceRangeAndCategory(@RequestParam int page, @RequestParam int size, @RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice, @RequestParam String category) {
        log.info("Получение продуктов по интервалу цены и категории");
        Page<GetProductResponse> productsByPriceRangeAndCategory = productService.getProductsByPriceRangeAndCategory(page, size, minPrice, maxPrice, category);
        log.info("продукты  по интервалу цены и категории: {}", productsByPriceRangeAndCategory);
        log.info("Суммарное кол-во страниц: {}", productsByPriceRangeAndCategory.getTotalPages());
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-pages", String.valueOf(productsByPriceRangeAndCategory.getTotalPages()));
        return ResponseEntity.ok()
                .headers(headers)
                .body(productsByPriceRangeAndCategory.getContent());
    }

    /**
     * Получает список продуктов по интервалу цены
     *
     * @return список продуктов
     */
    @GetMapping("/searchByPriceRange")
    public ResponseEntity<List<GetProductResponse>> getProductByPriceRangeAndCategory(@RequestParam int page, @RequestParam int size, @RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
        log.info("Получение продуктов по интервалу цены");
        Page<GetProductResponse> productsByPriceRange = productService.getProductsByPriceRange(page, size, minPrice, maxPrice);
        log.info("продукты по интервалу цены: {}", productsByPriceRange);
        log.info("Суммарное кол-во страниц: {}", productsByPriceRange.getTotalPages());
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-pages", String.valueOf(productsByPriceRange.getTotalPages()));
        return ResponseEntity.ok()
                .headers(headers)
                .body(productsByPriceRange.getContent());
    }


}
