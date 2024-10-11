package ru.sber.backend.services.product;

import org.springframework.data.domain.Page;
import ru.sber.backend.entities.product.Product;
import ru.sber.backend.models.product.GetProductResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(Long productId);

    GetProductResponse getProductByArticle(int productArticle);

    //если не передана категория, то получает all продукты
    Page<GetProductResponse> getProductsByCategoryId(int page, int size, Long category);

    Page<GetProductResponse> getProductsByName(int page, int size, String productName);

    Page<GetProductResponse> getProductsByPriceRangeAndCategory(int page, int size, BigDecimal minPrice, BigDecimal maxPrice, String category);

    Page<GetProductResponse> getProductsByPriceRange(int page, int size, BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> getProductsByPriceMin(BigDecimal minPrice);

    List<Product> getProductsByPriceMax(BigDecimal maxPrice);

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(Long productId);
}
