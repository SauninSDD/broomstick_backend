package ru.sber.backend.entities.cart;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import ru.sber.backend.entities.product.Product;

import java.math.BigDecimal;

@Entity
@Table(name = "clients_carts_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProduct { //некорректный какой-то вроде класс вплане полей-сущностей
    @Id
    @Column(name = "id_client_cart_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cart")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Column
    private int cartProductQuantity;

    @Column
    private BigDecimal cartProductPrice;

    @Override
    public String toString() {
        return "CartProduct{" +
                "id=" + id +
                ", cartId=" + cart.getId() +
                ", productId=" + product.getId() +
                ", cartProductQuantity=" + cartProductQuantity +
                ", cartProductPrice=" + cartProductPrice +
                '}';
    }
}
