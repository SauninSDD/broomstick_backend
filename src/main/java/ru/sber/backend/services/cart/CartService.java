package ru.sber.backend.services.cart;

import ru.sber.backend.models.cart.GetCartProductsResponse;

/**
 * Сервис для взаимодействия с корзиной клиента
 */
public interface CartService {
    /**
     * Получает список элементов корзины клиента
     *
     * @return список элементов корзины
     */
    GetCartProductsResponse getCartProducts();

    /**
     * Добавление товара в корзину
     *
     * @param productId Уникальный идентификатор товара
     * @return Возвращает статус добавления товара в корзину
     */
    boolean addToCart(Long productId);

    /**
     * Удаление товара из корзины
     *
     * @param productId Уникальный идентификатор товара
     * @return Возвращает статус удаления товара из корзины
     */
    boolean deleteFromCart(Long productId);

    /**
     * Удаляет все товара из корзины по id клиента
     */
//    boolean deleteAllDish();

    /**
     * Изменение количества товара в корзине
     *
     * @param productId   Уникальный идентификатор товара
     * @param quantity Количество добавляемого товара
     * @return Возвращает статус обновления количества товара в корзине
     */
    boolean updateProductQuantity(Long productId, int quantity);

    /**
     * Удаление корзины по ID клиента
     */
//    void deleteCart();

    /**
     * Выдает список идентификаторов блюд в корзине пользователя
     */
//    List<Long> getListOfDishIdsInCart();
}
