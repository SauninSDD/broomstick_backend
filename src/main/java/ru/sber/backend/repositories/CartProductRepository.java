package ru.sber.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.backend.entities.cart.CartProduct;

import java.util.List;

/**
 * Репозиторий для взаимодействия с элементами корзины клиента
 */
@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    /**
     * Получает список продуктов в корзине клиента
     *
     * @param cartId id корзины
     * @return список элементов корзины
     */
    List<CartProduct> findByCartId(long cartId);

    /**
     * Удаляет товар из корзины
     *
     * @param cartId id корзины
     * @param productId id товара
     */
    void deleteCartProductByCartIdAndProductId(long cartId, Long productId);
//
//    void deleteAllByCart_Id(long cartId);
}

