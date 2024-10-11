package ru.sber.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.enums.EOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "client_id", nullable = false)
    private String idClient;

    @Column(nullable = false)
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EOrderStatus orderStatus;

    @Column(nullable = false)
    private BigDecimal orderTotalPrice;

    @Column
    private String orderTrackNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderProduct> productsInOrder = new HashSet<>();


}
