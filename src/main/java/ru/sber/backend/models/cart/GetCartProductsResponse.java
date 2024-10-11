package ru.sber.backend.models.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.cart.Cart;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCartProductsResponse {

    private BigDecimal totalPrice;

    private int productValue;

    private List<CartProductDTO> cartProductsList;

    public GetCartProductsResponse(Cart cart) {
        this.totalPrice = cart.getClientCartTotalPrice();
        this.productValue = cart.getClientCartProductValue();
    }
}
