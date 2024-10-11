package ru.sber.backend.services.product;


import ru.sber.backend.entities.product.ProductCategory;
import ru.sber.backend.models.product.AddProductCategoryRequest;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getCategories();

    List<String> getSubCategories(Long productCategoryId);

    List<String> getNestedSubCategories(Long productCategoryId);

    ProductCategory getCategory(Long productCategoryId);

    boolean addProductCategory(AddProductCategoryRequest addProductCategoryRequest);

    boolean updateProductCategory();
}
