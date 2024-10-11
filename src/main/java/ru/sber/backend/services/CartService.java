package ru.sber.backend.services;

import ru.sber.backend.models.cart.GetCartProductsResponse;

import java.util.List;

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
     * Добавление блюда в корзину
     *
     * @param dishId Уникальный идентификатор блюда
     * @return Возвращает статус добавления блюда в корзину
     */
//    boolean addToCart(long dishId);

    /**
     * Удаление блюда из корзины
     *
     * @param dishId Уникальный идентификатор блюда
     * @return Возвращает статус удаления блюда из корзины
     */
//    boolean deleteDish(long dishId);

    /**
     * Удаляет все блюда из корзины по id клиента
     */
//    boolean deleteAllDish();

    /**
     * Изменение количества блюда в корзине
     *
     * @param dishId   Уникальный идентификатор блюда
     * @param quantity Количество добавляемого блюда
     * @return Возвращает статус обновления количества блюда в корзине
     */
//    boolean updateDishAmount(long dishId, int quantity);

    /**
     * Удаление корзины по ID клиента
     */
//    void deleteCart();

    /**
     * Выдает список идентификаторов блюд в корзине пользователя
     */
//    List<Long> getListOfDishIdsInCart();
}
