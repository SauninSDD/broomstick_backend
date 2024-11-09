package ru.sber.backend.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.backend.entities.product.ProductCategory;
import ru.sber.backend.models.product.category.AddProductCategoryRequest;
import ru.sber.backend.repositories.product.ProductCategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCategoryServiceImp implements ProductCategoryService{
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryServiceImp(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductCategory> getCategories() {
        return productCategoryRepository.findCategories();
    }

    @Override
    public List<String> getSubCategories(Long productCategoryId) {
        return productCategoryRepository.findByParentCategory_Id(productCategoryId).stream().map(ProductCategory::getName).toList();
    }

    @Override
    public List<String> getNestedSubCategories(Long parentId) {
        List<ProductCategory> subCategories = new ArrayList<>();
        getNestedSubCategoriesRecursive(parentId, subCategories);
        return subCategories.stream().map(ProductCategory::getName).toList();
    }

    @Override
    public ProductCategory getCategory(Long productCategoryId) {
        return null;
    }

    @Override
    public boolean addProductCategory(AddProductCategoryRequest addProductCategoryRequest) {
        return false;
    }

    @Override
    public boolean updateProductCategory() {
        return false;
    }

    private void getNestedSubCategoriesRecursive(Long parentId, List<ProductCategory> subCategories) {
        List<ProductCategory> foundSubCategories = productCategoryRepository.findByParentCategory_Id(parentId);
        for (ProductCategory subCategory : foundSubCategories) {
            subCategories.add(subCategory);
            getNestedSubCategoriesRecursive(subCategory.getId(), subCategories);
        }
    }
}
