package ru.sber.backend.models.cart;

import lombok.Data;

@Data
public class CartProductDTORequest {
    Long productId;
    int quantity;
}
