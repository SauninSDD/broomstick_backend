package ru.sber.backend.services.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.backend.entities.cart.Cart;
import ru.sber.backend.entities.cart.CartProduct;
import ru.sber.backend.entities.product.Product;
import ru.sber.backend.models.cart.CartProductDTO;
import ru.sber.backend.models.cart.GetCartProductsResponse;
import ru.sber.backend.repositories.CartProductRepository;
import ru.sber.backend.repositories.CartRepository;
import ru.sber.backend.repositories.product.ProductRepository;
import ru.sber.backend.services.client.ClientService;

import java.util.*;

@Slf4j
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ClientService clientService;
    private final ProductRepository productRepository;


    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartProductRepository cartProductRepository, ClientService clientService, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.clientService = clientService;
        this.productRepository = productRepository;
    }


    @Override
    public GetCartProductsResponse getCartProducts() {
        Optional<Cart> cart = cartRepository.findCartByIdClient(clientService.getIdClient());

        if (cart.isPresent()) {
            List<CartProductDTO> cartProducts = cartProductRepository.findByCartId(cart.get().getId()).stream().map(CartProductDTO::new).toList();
            log.info("Получаем список блюд в корзине {}", cartProducts);

            return new GetCartProductsResponse(cart.get(), cartProducts);
        }

        return null;
    }

    @Transactional
    @Override
    public boolean addToCart(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        // TODO Добавить проверку, что кол-ва товара не равно нулю, а также  добавить поле кол-ва товару (пока на фронте не дать добавлять).
        if (product.isPresent()) {
        String clientId = clientService.getIdClient();
        Cart cart = cartRepository.findCartByIdClient(clientId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setIdClient(clientId);
            log.info("Создание корзины пользователя");
            return cartRepository.save(newCart);
        });

        Optional<CartProduct> cartProduct = Optional.ofNullable(cart.getProductsInCart())
                .orElse(Collections.emptyList())
                .stream()
                .filter(item -> Objects.equals(item.getProduct().getId(), productId))
                .findFirst();

        if (cartProduct.isPresent()) {
            CartProduct existingCartProduct = cartProduct.get();
            existingCartProduct.setCartProductQuantity(existingCartProduct.getCartProductQuantity() + 1);
        } else {
            CartProduct newCartProduct = CartProduct.builder()
                    .cart(cart)
                    .product(product.get())
                    .cartProductQuantity(1)
                    .cartProductPrice(product.get().getProductPrice())
                    .build();
            cart.getProductsInCart().add(newCartProduct);
        }

        cartRepository.save(cart);

        return true;
        }

        else return false;
    }

    @Transactional
    @Override
    public boolean deleteFromCart(Long productId) {
        Optional<Cart> cart = cartRepository.findCartByIdClient(clientService.getIdClient());
        if (cart.isPresent()) {
            cartProductRepository.deleteCartProductByCartIdAndProductId(cart.get().getId(), productId);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean updateProductQuantity(Long productId, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findCartByIdClient(clientService.getIdClient());
        Optional<Product> product = productRepository.findById(productId);

        if (optionalCart.isPresent() && product.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartProduct> cartProducts = cart.getProductsInCart();

            Optional<CartProduct> cartProductToUpdate = cartProducts.stream()
                    .filter(item -> Objects.equals(item.getProduct().getId(), productId))
                    .findFirst();

            cartProductToUpdate.ifPresent(item -> {
                item.setCartProductQuantity(quantity);
                cartRepository.save(cart);
            });

            return cartProductToUpdate.isPresent();
        }

        return false;
    }

/*

    @Transactional
    @Override
    public boolean deleteAllDish() {
        Optional<Cart> cart = cartRepository.findCartByIdClient(getIdClient());
        if (cart.isPresent()) {
            log.info("Удаление товара из корзины: {} по id: {}", cart.get().getId(), getIdClient());
            cartProductRepository.deleteAllByCart_Id(cart.get().getId());
            return true;
        }
        return false;
    }



    @Override
    public void deleteCart() {
        Optional<Cart> cart = cartRepository.findCartByIdClient(getIdClient());

        if (cart.isPresent()) {
            long cartId = cart.get().getId();
            cartProductRepository.deleteAll();
            cartRepository.deleteById(cartId);
        }
    }

    @Override
    public List<Long> getListOfDishIdsInCart() {
        Optional<Cart> cart = cartRepository.findCartByIdClient(getIdClient());

        if (cart.isPresent()) {
            Cart shoppingCart = cart.get();

            return shoppingCart.getCartItems()
                    .stream()
                    .map(CartItem::getDishId)
                    .toList();
        }
        return Collections.emptyList();

    }


    private String getIdClient() {
        Jwt jwt = jwtService.getJwtSecurityContext();
        return jwtService.getSubClaim(jwt);
    }

*/}
