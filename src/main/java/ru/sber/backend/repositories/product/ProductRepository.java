package ru.sber.backend.repositories.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.backend.entities.product.Product;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByProductArticle(int articleProduct);

    Page<Product> findByCategoryId(Long categoryId, PageRequest of);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(concat('%', :nameProduct, '%'))")
    Page<Product> findByProductName(String nameProduct, PageRequest of);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND LOWER(p.productName) LIKE LOWER(concat('%', :nameProduct, '%'))")
    Page<Product> findByCategoryIdAndProductName(Long categoryId, String nameProduct, PageRequest of);

    @Query("SELECT p FROM Product p WHERE p.productPrice BETWEEN :minPrice AND :maxPrice AND p.category.name = :category ")
    Page<Product> findByPriceRangeAndCategoryName(BigDecimal minPrice, BigDecimal maxPrice, String category, PageRequest of);

    @Query("SELECT p FROM Product p WHERE p.productPrice between :minPrice and :maxPrice")
    Page<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, PageRequest of);
}
