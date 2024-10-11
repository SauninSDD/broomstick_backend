
package ru.sber.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.product.Product;

@Entity
@Embeddable
@Table(name = "orders_products")
@Data
@NoArgsConstructor
public class OrderProduct {
    @Id
    @Column(name = "id_order_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Column
    private byte orderProductValue;



}
