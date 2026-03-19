package ai.tanuj.ecomerce.Service;

import ai.tanuj.ecomerce.Model.CartResponse;

public interface CartService {
    CartResponse getCartForUser(String email);
    String addItemToCart(Long productId, Integer quantity, String email);
}
