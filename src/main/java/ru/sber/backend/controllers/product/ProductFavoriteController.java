package ru.sber.backend.controllers.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.backend.models.ApiResponse;
import ru.sber.backend.models.cart.CartProductDTORequest;
import ru.sber.backend.models.product.favorite.GetProductFavoritesResponse;
import ru.sber.backend.models.product.favorite.ProductFavoriteDTORequest;
import ru.sber.backend.services.product.ProductFavoriteService;

@Slf4j
@RestController
@RequestMapping("/favorites")
public class ProductFavoriteController {
    private final ProductFavoriteService productFavoriteService;

    @Autowired
    public ProductFavoriteController(ProductFavoriteService productFavoriteService) {
        this.productFavoriteService = productFavoriteService;
    }

    /**
     * Получает список избранных товаров.
     *
     * @return Список избранных товаров.
     */
    @PreAuthorize("hasRole('client_user')")
    @GetMapping
    public ResponseEntity<
            ApiResponse<GetProductFavoritesResponse>> getFavorites() {
        log.info("Список избранного: {}", productFavoriteService.getAllProductFavorites());
        GetProductFavoritesResponse response = productFavoriteService.getAllProductFavorites();

        return new ResponseEntity<>(new ApiResponse<>(true, response), HttpStatus.OK);
    }

    /**
     * Добавляет товар в избранное
     *
     * @param request DTO с id товара
     */
    @PreAuthorize("hasRole('client_user')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addToFavorites(@RequestBody ProductFavoriteDTORequest request) {
        log.info("Добавление в корзину продукт с id: {}", request);
        boolean isAddedProduct = productFavoriteService.addProductFavorite(request.getProductId());
        if (isAddedProduct) {
            return ResponseEntity.ok(new ApiResponse<>(true));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false));
        }
    }

    /**
     * Удаляет товар из избранного
     *
     * @param request DTO c id товара
     * @return корзина с внесенными изменениями
     */
    @PreAuthorize("hasRole('client_user')")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteFromFavorites(@RequestBody ProductFavoriteDTORequest request) {
        log.info("Удаление из корзины товара с id: {}", request.getProductId());

        boolean isDeleted = productFavoriteService.deleteProductFavorite(request.getProductId());

        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>(true));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false));
        }
    }

}
