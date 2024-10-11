package ru.sber.backend.entities.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client_addresses")
public class ClientAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientDeliveryDestinationRegion;

    @Column(nullable = false)
    private String clientDeliveryDestinationCity;

    @Column(nullable = false)
    private String clientDeliveryDestinationStreet;

    @Column(nullable = false)
    private String clientDeliveryDestinationBuilding;

    @Column
    private String clientDeliveryDestinationApartment;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    @JsonIgnore
    private Client client;
}
