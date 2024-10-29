package ru.sber.backend.models.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sber.backend.entities.product.ProductCategory;

import java.util.List;

@Data
@AllArgsConstructor
public class GetCategoryResponse {
    private List<ProductCategory> categories;
}
