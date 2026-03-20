package ai.tanuj.ecomerce.Service;

import ai.tanuj.ecomerce.Entity.CartItem;
import ai.tanuj.ecomerce.Entity.ProductEntity;
import ai.tanuj.ecomerce.Entity.UserEntity;
import ai.tanuj.ecomerce.Model.CartItemDTO;
import ai.tanuj.ecomerce.Model.CartResponse;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ai.tanuj.ecomerce.Repository.CartRepository;
import ai.tanuj.ecomerce.Repository.UserRepository;
import jakarta.transaction.Transactional;
import ai.tanuj.ecomerce.Repository.ProductRepository;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    @Transactional
    public String addItemToCart(Long productId, Integer quantity, String email) {
        // 1. Find the User
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Find the Product
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 3. STOCK VALIDATION: Check if enough stock exists
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock! Only " + product.getStockQuantity() + " available.");
        }

        // 4. DUPLICATE CHECK: Is this product already in Tanuj's cart?
        Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(user, product);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;
            
            // Check stock again for the combined total
            if (product.getStockQuantity() < newQuantity) {
                throw new RuntimeException("Total cart quantity exceeds available stock." + "\n" + "Available stock: " + product.getStockQuantity());
            }
            
            item.setQuantity(newQuantity);
            cartRepository.save(item);
        } else {
            // Create a brand new Cart entry
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartRepository.save(newItem);
        }

        return "Product added to cart successfully!";
    }
    
    public CartResponse getCartForUser(String email) {
        // 1. Find the User
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get the ugly Entities from DB
        List<CartItem> cartItems = cartRepository.findByUser(user);

        // 3. Map Entities to DTOs (The Cleanup)
        List<CartItemDTO> dtoList = cartItems.stream().map(item -> {
            CartItemDTO dto = new CartItemDTO();
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setProductDescription(item.getProduct().getDescription());
            dto.setUnitPrice(item.getProduct().getPrice());
            dto.setQuantity(item.getQuantity());
            dto.setSubTotal(item.getSubTotal()); // Using the helper method from the Entity
            return dto;
        }).toList();

        // 4. Calculate Grand Total
        Double grandTotal = dtoList.stream()
                .mapToDouble(CartItemDTO::getSubTotal)
                .sum();

        return new CartResponse(dtoList, grandTotal);
}
}
    

