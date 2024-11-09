package ru.sber.backend.models.product.favorite;

import lombok.Data;
import ru.sber.backend.entities.product.ProductFavorite;
import ru.sber.backend.models.product.ProductDTO;

@Data
public class ProductFavoriteDTO {
    private Long id;

    private ProductDTO productDTO;

    public ProductFavoriteDTO(ProductFavorite productFavorite) {
        this.id = productFavorite.getId();
        this.productDTO = new ProductDTO(productFavorite.getProduct());
    }
}
