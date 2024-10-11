/*
package ru.sber.backend.services.product;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sber.backend.entities.product.ProductCategory;
import ru.sber.backend.models.product.AddProductCategoryRequest;
import ru.sber.backend.repositories.product.ProductCategoryRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Disabled
public class ProductCategoryServiceImpTest {

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductCategoryServiceImp productCategoryService;

    @Test
    public void testGetCategories() {
        // Arrange
        List<ProductCategory> mockCategories = Arrays.asList(
                new ProductCategory(1L, "Category1", null),
                new ProductCategory(2L, "Category2", null)
        );
        when(productCategoryRepository.findCategories()).thenReturn(mockCategories);

        // Act
        List<ProductCategory> categories = productCategoryService.getCategories();

        // Assert
        assertEquals(2, categories.size());
        assertEquals("Category1", categories.get(0));
        assertEquals("Category2", categories.get(1));
    }

    @Test
    public void testGetSubCategories() {
        // Arrange
        Long categoryId = 1L;
        List<ProductCategory> mockSubCategories = Collections.singletonList(
                new ProductCategory(2L, "SubCategory1", new ProductCategory(1L, "Category1", null))
        );
        when(productCategoryRepository.findByParentCategory_Id(categoryId)).thenReturn(mockSubCategories);

        // Act
        List<String> subCategories = productCategoryService.getSubCategories(categoryId);

        // Assert
        assertEquals(1, subCategories.size());
        assertEquals("SubCategory1", subCategories.get(0));
    }

    @Test
    public void testGetNestedSubCategories() {
        // Arrange
        Long categoryId = 1L;
        List<ProductCategory> mockNestedSubCategories = Arrays.asList(
                new ProductCategory(2L, "SubCategory1", new ProductCategory(1L, "Category1", null)),
                new ProductCategory(3L, "SubCategory2", new ProductCategory(2L, "SubCategory1", new ProductCategory(1L, "Category1", null)))
        );
        when(productCategoryRepository.findByParentCategory_Id(categoryId)).thenReturn(mockNestedSubCategories);

        // Act
        List<String> nestedSubCategories = productCategoryService.getNestedSubCategories(categoryId);

        // Assert
        assertEquals(2, nestedSubCategories.size());
        assertTrue(nestedSubCategories.contains("SubCategory1"));
        assertTrue(nestedSubCategories.contains("SubCategory2"));
    }

    @Test
    public void testAddProductCategory() {
        // Arrange
        AddProductCategoryRequest request = new AddProductCategoryRequest();
        // Act
        boolean result = productCategoryService.addProductCategory(request);

        // Assert
        assertFalse(result);
    }


}
*/
