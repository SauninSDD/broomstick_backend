package ru.sber.backend.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.sber.backend.entities.product.Product;
import ru.sber.backend.exceptions.ProductNotFound;
import ru.sber.backend.models.product.ProductDTO;
import ru.sber.backend.repositories.product.ProductCategoryRepository;
import ru.sber.backend.repositories.product.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public ProductDTO getProductByArticle(int productArticle) {
        Product product = productRepository.findByProductArticle(productArticle);
        if (product == null) {
            throw new ProductNotFound("Продукт не найден");
        } else {
            return new ProductDTO(product);
        }
    }


    @Override
    public Page<ProductDTO> getProductsByCategoryId(int page, int size, Long categoryId) {
        boolean isExistCategory = productCategoryRepository.existsById(categoryId);
        Page<Product> productsPage;
        if (isExistCategory) {
            productsPage = productRepository.findByCategoryId(categoryId, PageRequest.of(page, size));
        } else {
            productsPage = productRepository.findAll(PageRequest.of(page, size));
        }
        return productsPage.map(getGetProductResponseFunction());
    }


    @Override
    public Page<ProductDTO> getProductsByName(int page, int size, String productName) {
        Page<Product> productsByName = productRepository.findByProductName(productName, PageRequest.of(page, size));
        return productsByName.map(getGetProductResponseFunction());
    }

    @Override
    public Page<ProductDTO> getProductsByPriceRangeAndCategory(int page, int size, BigDecimal minPrice, BigDecimal maxPrice, String category) {
        Page<Product> productsByPriceRangeAndCategory = productRepository.findByPriceRangeAndCategoryName(minPrice, maxPrice, category, PageRequest.of(page, size));
        return productsByPriceRangeAndCategory.map(getGetProductResponseFunction());
    }

    @Override
    public Page<ProductDTO> getProductsByPriceRange(int page, int size, BigDecimal minPrice, BigDecimal maxPrice) {
        Page<Product> productsByPriceRange = productRepository.findByPriceRange(minPrice, maxPrice, PageRequest.of(page, size));
        return productsByPriceRange.map(getGetProductResponseFunction());
    }

    @Override
    public List<Product> getProductsByPriceMin(BigDecimal minPrice) {
        return null;
    }

    @Override
    public List<Product> getProductsByPriceMax(BigDecimal maxPrice) {
        return null;
    }

    @Override
    public boolean addProduct(Product product) {
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }

    @Override
    public boolean deleteProduct(Long productId) {
        return false;
    }

    /**
     * Преобразует класс Product {@link Product} в {@link ProductDTO}
     *
     * @return GetProductResponse
     */
    private Function<Product, ProductDTO> getGetProductResponseFunction() {
        return ProductDTO::new;
    }


}
