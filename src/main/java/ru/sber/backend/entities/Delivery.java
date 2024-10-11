package ru.sber.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deliveries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String deliveryDestinationRegion;

    @Column(nullable = false)
    private String deliveryDestinationCity;

    @Column(nullable = false)
    private String deliveryDestinationStreet;

    @Column(nullable = false)
    private String deliveryDestinationBuilding;

    @Column
    private String deliveryDestinationApartment;

    @OneToOne
    @JoinColumn(name = "id_order", nullable = false)
    @JsonIgnore
    private Order order;
}
