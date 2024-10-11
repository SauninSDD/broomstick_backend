package ru.sber.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int receiptTotalValue;

    @Column(nullable = false)
    private int receiptCardNumber;

    @Column(nullable = false)
    private int idClient;

    @Column(nullable = false)
    private LocalDate receiptDate;

    @OneToOne
    @JoinColumn(name = "id_order", nullable = false)
    @JsonIgnore
    private Order order;


}
