package ru.sber.backend.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.backend.entities.product.ProductFavorite;

import java.util.List;

@Repository
public interface ProductFavoriteRepository extends JpaRepository<ProductFavorite, Long> {
    List<ProductFavorite> findCartByIdClient(String idClient);
    boolean existsByProductIdAndIdClient(Long productId, String idClient);

    void deleteByProductIdAndIdClient(Long productId, String idClient);

}
