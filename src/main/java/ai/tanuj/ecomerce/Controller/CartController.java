package ai.tanuj.ecomerce.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.tanuj.ecomerce.Model.CartResponse;
import ai.tanuj.ecomerce.Service.CartService;
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // POST: http://localhost:8080/api/cart/add?productId=1&quantity=2
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            Authentication auth) {
        
        String response = cartService.addItemToCart(productId, quantity, auth.getName());
        return ResponseEntity.ok(response);
    }

    // GET: http://localhost:8080/api/cart
    @GetMapping
    public ResponseEntity<CartResponse> getMyCart(Authentication auth) {
        return ResponseEntity.ok(cartService.getCartForUser(auth.getName()));
    }
}