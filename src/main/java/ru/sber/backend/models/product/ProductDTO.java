package ru.sber.backend.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.product.Product;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private int weight;

    private int width;

    private int height;

    private int length;

    private int article;

    private String imageUrl;

    private String category;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getProductName();
        this.price = product.getProductPrice();
        this.description = product.getProductDescription();
        this.width = product.getProductWidth();
        this.height = product.getProductHeight();
        this.length = product.getProductLength();
        this.article = product.getProductArticle();
        this.imageUrl = product.getImageUrl();
        this.category = product.getCategory().getName();
    }
}
