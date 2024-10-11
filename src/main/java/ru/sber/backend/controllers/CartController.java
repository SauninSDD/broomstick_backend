package ru.sber.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.backend.models.cart.GetCartProductsResponse;
import ru.sber.backend.services.CartService;
import ru.sber.backend.services.product.ProductService;

import java.util.List;

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
    @GetMapping()
    public ResponseEntity<GetCartProductsResponse> getDishesCart() {
        log.info("Список блюд в корзине: {}", cartService.getCartProducts());



        return ResponseEntity.ok().body(cartService.getCartProducts());
    }
/*


    */
/**
     * Добавляет блюдо в корзину
     *
     * @param dishId id блюда
     * @return корзину с добавленными блюдами
     *//*

    @PreAuthorize("hasRole('client_user')")
    @PostMapping("/dish/{dishId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long dishId) {
        log.info("Добавление в корзину блюда с id: {}", dishId);
        boolean recordInserted = cartService.addToCart(dishId);
        if (recordInserted) {
            return ResponseEntity.ok("Блюдо успешно добавлено в корзину");
        } else {
            return ResponseEntity.badRequest().body("Не удалось добавить блюдо в корзину");
        }
    }

    */
/**
     * Обновляет количество блюда в корзине
     *
     * @param dishId id блюда
     * @param dish   блюда, у которого изменяется количество
     * @return корзину с измененным количеством блюда
     *//*

    @PreAuthorize("hasRole('client_user')")
    @PutMapping("/dish/{dishId}")
    public ResponseEntity<String> updateCartItemQuantity(@PathVariable long dishId, @RequestBody CartItem dish) {
        log.info("Изменяется количество товара на {} в корзине", dish.getQuantity());
        boolean recordUpdated = cartService.updateDishAmount(dishId, dish.getQuantity());

        if (recordUpdated) {
            return ResponseEntity.ok("Количество блюд изменено");
        } else {
            return ResponseEntity.badRequest().body("Не удалось изменить блюдо");
        }
    }

    */
/**
     * Удаляет блюдо из корзины
     *
     * @param dishId id блюда
     * @return корзина с внесенными изменениями
     *//*

    @PreAuthorize("hasRole('client_user')")
    @DeleteMapping("/dish/{dishId}")
    public ResponseEntity<String> deleteDish(@PathVariable long dishId) {

        log.info("Удаление из корзины блюда с id: {}", dishId);

        boolean isDeleted = cartService.deleteDish(dishId);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Не удалось удалить блюдо");
        }
    }
*/
}
