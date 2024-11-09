package ru.sber.backend.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.cart.CartProduct;
import ru.sber.backend.entities.Measure;
import ru.sber.backend.entities.OrderProduct;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(columnNames = "productArticle")})
public class Product {

    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String productName;

    @Column(nullable = false)
    private BigDecimal productPrice;

    @Column
    private String productDescription;

    @Column(nullable = false)
    private int productWeight;

    @Column(nullable = false)
    private int productWidth;

    @Column(nullable = false)
    private int productHeight;

    @Column(nullable = false)
    private int productLength;


    @Column(nullable = false, unique = true)
    private int productArticle;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "id_measure", nullable = false)
    @JsonIgnore
    private Measure measure;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    @JsonIgnore
    private ProductCategory category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductFeedback> productFeedbacks = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<CartProduct> productsInCart = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> productsInOrder = new HashSet<>();
}
