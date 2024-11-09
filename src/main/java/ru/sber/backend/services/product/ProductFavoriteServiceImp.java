package ru.sber.backend.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.backend.entities.product.Product;
import ru.sber.backend.entities.product.ProductFavorite;
import ru.sber.backend.models.product.favorite.GetProductFavoritesResponse;
import ru.sber.backend.models.product.favorite.ProductFavoriteDTO;
import ru.sber.backend.repositories.product.ProductFavoriteRepository;
import ru.sber.backend.repositories.product.ProductRepository;
import ru.sber.backend.services.client.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductFavoriteServiceImp implements ProductFavoriteService {
    private final ProductFavoriteRepository productFavoriteRepository;
    private final ClientService clientService;
    private final ProductRepository productRepository;


    @Autowired
    public ProductFavoriteServiceImp(ProductFavoriteRepository productFavoriteRepository, ClientService clientService, ProductRepository productRepository) {
        this.productFavoriteRepository = productFavoriteRepository;
        this.clientService = clientService;
        this.productRepository = productRepository;
    }

    @Override
    public GetProductFavoritesResponse getAllProductFavorites() {
        List<ProductFavoriteDTO> productFavorites = productFavoriteRepository.findCartByIdClient(clientService.getIdClient()).stream().map(ProductFavoriteDTO:: new).toList();

        return new GetProductFavoritesResponse(productFavorites);
    }

    @Transactional
    @Override
    public boolean addProductFavorite(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            String clientId = clientService.getIdClient();
            boolean isExistInFavorites = productFavoriteRepository.existsByProductIdAndIdClient(productId, clientId);

        if (!isExistInFavorites) {
            productFavoriteRepository.save(
                    ProductFavorite.builder()
                            .product(product.get())
                            .idClient(clientId)
                            .build()
            );

            return true;
        }}

        return false;
    }

    @Transactional
    @Override
    public boolean deleteProductFavorite(Long productId) {
        String clientId = clientService.getIdClient();
        boolean isExistInFavorites = productFavoriteRepository.existsByProductIdAndIdClient(productId, clientId);

        if (isExistInFavorites) {
            productFavoriteRepository.deleteByProductIdAndIdClient(productId, clientId);

            return true;
        }

        return false;
    }
}
