package ru.sber.backend.services.product;

import org.springframework.data.domain.Page;
import ru.sber.backend.entities.product.Product;
import ru.sber.backend.models.product.ProductDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(Long productId);

    ProductDTO getProductByArticle(int productArticle);

    //если не передана категория, то получает all продукты
    Page<ProductDTO> getProductsByCategoryId(int page, int size, Long category);

    Page<ProductDTO> getProductsByName(int page, int size, String productName);

    Page<ProductDTO> getProductsByPriceRangeAndCategory(int page, int size, BigDecimal minPrice, BigDecimal maxPrice, String category);

    Page<ProductDTO> getProductsByPriceRange(int page, int size, BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> getProductsByPriceMin(BigDecimal minPrice);

    List<Product> getProductsByPriceMax(BigDecimal maxPrice);

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(Long productId);
}
