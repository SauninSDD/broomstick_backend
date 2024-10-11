package ru.sber.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.backend.entities.cart.Cart;
import ru.sber.backend.models.cart.CartProductDTO;
import ru.sber.backend.models.cart.GetCartProductsResponse;
import ru.sber.backend.repositories.CartProductRepository;
import ru.sber.backend.repositories.CartRepository;
import ru.sber.backend.services.client.ClientService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ClientService clientService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartProductRepository cartProductRepository, ClientService clientService) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.clientService = clientService;
    }


    @Override
    public GetCartProductsResponse getCartProducts() {
        Optional<Cart> cart = cartRepository.findCartByIdClient(clientService.getIdClient());

        if (cart.isPresent()) {
            GetCartProductsResponse response = new GetCartProductsResponse(cart.get());

            log.info("Получаем список блюд в корзине {}", cartProductRepository.findByCartId(cart.get().getId()));
            List<CartProductDTO> listCartProducts = cartProductRepository.findByCartId(cart.get().getId()).stream().map(CartProductDTO::new).toList();

            response.setCartProductsList(listCartProducts);
            return response;
        }

        return null;
    }

   /* @Transactional
    @Override
    public boolean addToCart(long dishId) {
        String clientId = getIdClient();

        Optional<ClientCart> cart = cartRepository.findCartByIdClient(clientId);

        Cart shoppingCart = cart.orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setClient(clientId);
            log.info("Создание корзины пользователя");
            return cartRepository.save(newCart);
        });

        Optional<CartItem> cartItem = Optional.ofNullable(shoppingCart.getCartItems())
                .orElse(Collections.emptyList())
                .stream()
                .filter(item -> item.getDishId() == dishId)
                .findFirst();

        if (cartItem.isPresent()) {
            CartItem existingCartItem = cartItem.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
        } else {

            CartItem newCartItem = new CartItem();
            newCartItem.setCart(shoppingCart);
            newCartItem.setDishId(dishId);
            newCartItem.setQuantity(1);
            if(shoppingCart.getCartItems() == null) {
                shoppingCart.setCartItems(new ArrayList<>());
            }
            shoppingCart.getCartItems().add(newCartItem);
        }

        cartRepository.save(shoppingCart);

        return true;

    }


    @Override
    @Transactional
    public boolean deleteDish(long dishId) {
        Optional<Cart> cart = cartRepository.findCartByIdClient(getIdClient());
        if (cart.isPresent()) {
            cartProductRepository.deleteCartItemByCartIdAndDishId(cart.get().getId(), dishId);
            return true;
        }
        return false;
    }

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
    public boolean updateDishAmount(long dishId, int quantity) {
        Optional<Cart> cart = cartRepository.findCartByIdClient(getIdClient());

        if (cart.isPresent()) {
            Cart shoppingCart = cart.get();
            List<CartItem> cartItems = shoppingCart.getCartItems();

            Optional<CartItem> cartItemToUpdate = cartItems.stream()
                    .filter(item -> item.getDishId() == dishId)
                    .findFirst();

            cartItemToUpdate.ifPresent(item -> {
                item.setQuantity(quantity);
                cartRepository.save(shoppingCart);
            });

            return cartItemToUpdate.isPresent();
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
