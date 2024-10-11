package ru.sber.backend.entities.client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Table(name = "clients_cards")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String clientCardNumber;

    @Column(nullable = false)
    private int clientCardCvc;

    @Column(nullable = false)
    private LocalDate clientCardExpirationDate;

    @Column(nullable = false)
    private String clientCardOwner;

    @Column(name = "client_id", nullable = false)
    private String idClient;
}
