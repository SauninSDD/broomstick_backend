package ru.sber.backend.controllers.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.backend.models.ApiResponse;
import ru.sber.backend.models.cart.CartProductDTORequest;
import ru.sber.backend.models.cart.GetCartProductsResponse;
import ru.sber.backend.services.cart.CartService;
import ru.sber.backend.services.product.ProductService;

/**
 * Контроллер для обработки запросов к корзине клиента
 */
@Slf4j
@RestController
@RequestMapping("cart")
public class CartController {

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    /**
     * Получает список товаров по id корзины
     *
     * @return получение списка товаров
     */
    @PreAuthorize("hasRole('client_user')")
    @GetMapping
    public ResponseEntity<
    ApiResponse<GetCartProductsResponse>> getCart() {
        log.info("Список блюд в корзине: {}", cartService.getCartProducts());
        GetCartProductsResponse response = cartService.getCartProducts();
        if (response != null) {
            return new ResponseEntity<>(new ApiResponse<>(true, response), HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false));
        }
    }

    /**
     * Добавляет товар в корзину
     *
     * @param productIdDTO id товара
     */
    @PreAuthorize("hasRole('client_user')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addToCart(@RequestBody CartProductDTORequest productIdDTO) {
        log.info("Добавление в корзину продукт с id: {}", productIdDTO);
        boolean isAddedProduct = cartService.addToCart(productIdDTO.getProductId());
        if (isAddedProduct) {
            return ResponseEntity.ok(new ApiResponse<>(true));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false)/*"Не удалось добавить товар в корзину"*/);
        }
    }

    
/**
     * Обновляет количество товара в корзине
     *
     * @param productIdDTO Товар, у которого изменяется количество
     */
    @PreAuthorize("hasRole('client_user')")
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateCartProductQuantity(@RequestBody CartProductDTORequest productIdDTO) {
        log.info("Изменяется количество товара на {} в корзине", productIdDTO.getQuantity());
        boolean isUpdated = cartService.updateProductQuantity(productIdDTO.getProductId(), productIdDTO.getQuantity());

        if (isUpdated) {
            return ResponseEntity.ok(new ApiResponse<>(true));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false)/*"Не удалось удалить товар"*/);
        }
    }

    /**
     * Удаляет товар из корзины
     *
     * @param productIdDTO DTO c id товара
     * @return корзина с внесенными изменениями
     */

    @PreAuthorize("hasRole('client_user')")
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<ApiResponse<Void>> deleteFromCart(@RequestBody CartProductDTORequest productIdDTO) {
        log.info("Удаление из корзины товара с id: {}", productIdDTO.getProductId());

        boolean isDeleted = cartService.deleteFromCart(productIdDTO.getProductId());

        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>(true));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false)/*"Не удалось удалить товар"*/);
        }
    }
}
