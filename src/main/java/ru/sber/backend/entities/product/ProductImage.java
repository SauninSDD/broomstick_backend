package ru.sber.backend.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_images")

public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  /*  @Lob
    @Column(name = "image", columnDefinition="BLOB")
    private byte[] image; */

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    @JsonIgnore
    private Product product;
}
