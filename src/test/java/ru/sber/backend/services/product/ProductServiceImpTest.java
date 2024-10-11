/*
package ru.sber.backend.services.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.sber.backend.entities.product.Product;
import ru.sber.backend.exceptions.ProductNotFound;
import ru.sber.backend.models.product.GetProductResponse;
import ru.sber.backend.repositories.product.ProductCategoryRepository;
import ru.sber.backend.repositories.product.ProductRepository;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@Disabled
class ProductServiceImpTest {

    @InjectMocks
    private ProductServiceImp service;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void getProductByArticleSuccessSaunin() {
        int productArticle = 123;
        Product mockProduct = new Product();
        mockProduct.setProductArticle(productArticle);

        when(productRepository.findByProductArticle(productArticle)).thenReturn(mockProduct);

        GetProductResponse result = service.getProductByArticle(productArticle);

        assertNotNull(result);
        assertEquals(productArticle, result.getProductArticle());
    }

    @Test
    public void getProductByArticleExceptionProductNotFoundSaunin() {
        int productArticle = 123;
        when(productRepository.findByProductArticle(productArticle)).thenReturn(null);

        assertThrows(ProductNotFound.class, () -> service.getProductByArticle(productArticle));
    }

    @Test
    public void shouldQueryRepositoryForProductByArticleWhenRequestedSaunin() {
        int productArticle = 123;
        Product mockProduct = new Product();
        when(productRepository.findByProductArticle(productArticle)).thenReturn(mockProduct);

        service.getProductByArticle(productArticle);

        verify(productRepository).findByProductArticle(productArticle);
    }


    @Test
    public void getProductsByCategory_WhenCategoryIsNotEmptySaunin() {
        String category = "Test Category";
        List<Product> products = Arrays.asList(new Product());
        List<GetProductResponse> getProductResponses = Arrays.asList(new GetProductResponse());
        Page<Product> productsPage = new PageImpl<>(products);
        Page<GetProductResponse> getProductResponsesPage = new PageImpl<>(getProductResponses);

        when(productCategoryRepository.existsByCategoryName(category)).thenReturn(true);
        when(productRepository.findByCategoryCategoryName(eq(category), any(PageRequest.class))).thenReturn(productsPage);

        Page<GetProductResponse> result = service.getProductsByCategoryId(0, 10, category);

        assertEquals(getProductResponsesPage, result);
    }

    @Test
    public void getProductsByCategory_ReturnsAllProducts_WhenCategoryIsEmptySaunin() {
        String category = "";
        List<Product> products = Arrays.asList(new Product());
        List<GetProductResponse> getProductResponses = Arrays.asList(new GetProductResponse());
        Page<Product> productsPage = new PageImpl<>(products);
        Page<GetProductResponse> getProductResponsesPage = new PageImpl<>(getProductResponses);

        when(productCategoryRepository.existsByCategoryName(category)).thenReturn(false);
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(productsPage);

        Page<GetProductResponse> result = service.getProductsByCategoryId(0, 10, category);

        assertEquals(getProductResponsesPage, result);
    }

    @Test
    public void getProductsByCategory_WhenNumberPageIsNegativeSaunin() {
        String category = "Existing Category";
        int invalidPageIndex = -1;

        when(productCategoryRepository.existsByCategoryName(category)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.getProductsByCategoryId(invalidPageIndex, 10, category));
    }

    @Test
    public void getProductsByCategory_WhenSizePageIsNegativeSaunin() {
        String category = "Existing Category";
        int invalidPageSize = -1;

        when(productCategoryRepository.existsByCategoryName(category)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.getProductsByCategoryId(0, invalidPageSize, category));
    }

    @Test
    public void getProductsByCategory_WhenSizeIsOneSaunin() {
        String category = "Test Category";
        List<Product> products = Arrays.asList(new Product(), new Product());
        List<GetProductResponse> getProductResponses = Arrays.asList(new GetProductResponse(), new GetProductResponse());
        Page<Product> productsPage = new PageImpl<>(products);
        Page<GetProductResponse> getProductResponsesPage = new PageImpl<>(getProductResponses);

        when(productCategoryRepository.existsByCategoryName(category)).thenReturn(true);
        when(productRepository.findByCategoryCategoryName(eq(category), eq(PageRequest.of(0, 1))))
                .thenReturn(productsPage);

        Page<GetProductResponse> result = service.getProductsByCategoryId(0, 1, category);
        assertEquals(getProductResponsesPage, result);
    }

    @Test
    public void getProductsByCategory_WhenSizeMoreThenOneSaunin() {
        String category = "Test Category";
        List<Product> products = Arrays.asList(new Product(), new Product());
        List<GetProductResponse> getProductResponses = Arrays.asList(new GetProductResponse(), new GetProductResponse());
        Page<Product> productsPage = new PageImpl<>(products);
        Page<GetProductResponse> getProductResponsesPage = new PageImpl<>(getProductResponses);

        when(productCategoryRepository.existsByCategoryName(category)).thenReturn(true);
        when(productRepository.findByCategoryCategoryName(eq(category), eq(PageRequest.of(1, 5))))
                .thenReturn(productsPage);

        Page<GetProductResponse> result = service.getProductsByCategoryId(1, 5, category);
        assertEquals(getProductResponsesPage, result);
    }
}*/
