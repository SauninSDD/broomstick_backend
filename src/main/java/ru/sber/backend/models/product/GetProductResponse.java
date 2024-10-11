package ru.sber.backend.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.product.Product;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductResponse {
    private Long id;

    private String productName;

    private BigDecimal productPrice;

    private String productDescription;

    private int productWeight;

    private int productWidth;

    private int productHeight;

    private int productLength;

    private int productArticle;

    public GetProductResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.productDescription = product.getProductDescription();
        this.productWeight = product.getProductWeight();
        this.productWidth = product.getProductWidth();
        this.productHeight = product.getProductHeight();
        this.productLength = product.getProductLength();
        this.productArticle = product.getProductArticle();
    }
}
