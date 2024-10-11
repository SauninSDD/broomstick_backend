package ru.sber.backend.entities.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.product.Product;
import ru.sber.backend.entities.product.ProductFeedback;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name = "clients", uniqueConstraints = {@UniqueConstraint(columnNames = "clientLogin"), @UniqueConstraint(columnNames = "clientEmail")})
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @Column(name = "id_client")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 20)
    private String clientLogin;

    @Column(nullable = false)
    @Size(max = 50)
    @Email
    private String clientEmail;

    @Column(nullable = false)
    @Size(max = 120)
    private String clientPassword;

    @Column
    private String clientSex;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate clientDateOfBirth;

    @Column(nullable = false)
    @Size(max = 20)
    private String clientName;

    @OneToMany(mappedBy = "client")
    private Set<ProductFeedback> productFeedbacks = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "clients_favorites",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_product"))
    private Set<Product> productsFavorites = new HashSet<>();
}
