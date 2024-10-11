package ru.sber.backend.services.product;

import ru.sber.backend.entities.product.Product;

import java.util.List;

//по реализации избранных надо решить, т.к. теперь нет таблы клиента тут
public interface ProductFavoriteService {
    List<Product> getAllProductFavoritesByClientId();

    boolean addProductFavorite(Long productId);

    boolean deleteProductFavorite(Long productId);
}
