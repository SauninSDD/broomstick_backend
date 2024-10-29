package ru.sber.backend.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetCatalogResponse {
    List<ProductDTO> products;
    int totalPage;
}
