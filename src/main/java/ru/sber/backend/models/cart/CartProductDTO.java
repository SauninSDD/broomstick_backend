package ru.sber.backend.models.cart;

import lombok.Data;
import ru.sber.backend.entities.cart.CartProduct;
import ru.sber.backend.models.product.ProductDTO;

import java.math.BigDecimal;

@Data
public class CartProductDTO {
    private Long id;

    private BigDecimal cartProductPrice;

    private int cartProductQuantity;

    private ProductDTO productDTO;

    public CartProductDTO(CartProduct cartProduct) {
        this.id = cartProduct.getId();
        this.cartProductPrice = cartProduct.getCartProductPrice();
        this.cartProductQuantity = cartProduct.getCartProductQuantity();
        this.productDTO = new ProductDTO(cartProduct.getProduct());
    }
}
