package ru.sber.backend.models.product.favorite;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetProductFavoritesResponse {
    List<ProductFavoriteDTO> productFavorites;
}
