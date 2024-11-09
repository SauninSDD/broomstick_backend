package ru.sber.backend.services.product;

import ru.sber.backend.models.product.favorite.GetProductFavoritesResponse;

//по реализации избранных надо решить, т.к. теперь нет таблы клиента тут
public interface ProductFavoriteService {
    GetProductFavoritesResponse getAllProductFavorites();

    boolean addProductFavorite(Long productId);

    boolean deleteProductFavorite(Long productId);
}
