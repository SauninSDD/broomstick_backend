package ru.sber.backend.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.backend.entities.product.ProductCategory;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT pc FROM ProductCategory pc WHERE pc.parentCategory is null")
    List<ProductCategory> findCategories();

    List<ProductCategory> findByParentCategory_Id(Long parentId);

    boolean existsByName(String category);

}
