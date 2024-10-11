package ru.sber.backend.models.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.cart.CartProduct;
import ru.sber.backend.models.product.GetProductResponse;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDTO {
    private Long id;

    private BigDecimal cartProductPrice;

    private int cartProductQuantity;

    private GetProductResponse getProductResponse;

    public CartProductDTO(CartProduct cartProduct) {
        this.id = cartProduct.getId();
        this.cartProductPrice = cartProduct.getCartProductPrice();
        this.cartProductQuantity = cartProduct.getCartProductQuantity();
        this.getProductResponse = new GetProductResponse(cartProduct.getProduct());
    }
}
