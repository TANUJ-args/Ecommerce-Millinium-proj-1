package ai.tanuj.ecomerce.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.tanuj.ecomerce.Entity.CartItem;
import ai.tanuj.ecomerce.Repository.CartRepository;
import ai.tanuj.ecomerce.Repository.OrderRepository;
import ai.tanuj.ecomerce.Repository.ProductRepository;
import ai.tanuj.ecomerce.Repository.UserRepository;
import ai.tanuj.ecomerce.Entity.OrderEntity;
import ai.tanuj.ecomerce.Entity.OrderItem;
import ai.tanuj.ecomerce.Entity.ProductEntity;
import ai.tanuj.ecomerce.Entity.UserEntity;
import ai.tanuj.ecomerce.Model.OrderItemDTO;
import ai.tanuj.ecomerce.Model.OrderResponse;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private CartRepository cartRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    @Override
    @Transactional // CRITICAL: If any step fails, the whole order is cancelled
    public String checkout(String email) {
        // 1. Get User and their Cart
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<CartItem> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty()) throw new RuntimeException("Cart is empty!");

        // 2. Create the Order Summary
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setOrderTrackingNumber(UUID.randomUUID().toString());
        order.setStatus("PENDING");
        order.setDateCreated(LocalDateTime.now());

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        // 3. Process each item
        for (CartItem cartItem : cartItems) {
            ProductEntity product = cartItem.getProduct();

            // Double Check Stock
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Stock ran out for: " + product.getName());
            }

            // Decrease Stock in Database
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            // Create Order Item (The Snapshot)
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);

            total += (product.getPrice() * cartItem.getQuantity());
        }

        // 4. Finalize and Save
        order.setOrderItems(orderItems);
        order.setTotalAmount(total);
        orderRepository.save(order);

        // 5. CLEAR THE CART
        cartRepository.deleteByUser(user);

        return "Order Placed! Your Tracking ID is: " + order.getOrderTrackingNumber();
    }

    @Override
    public List<OrderResponse> getOrderHistory(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderEntity> orders = orderRepository.findByUserOrderByDateCreatedDesc(user);

        return orders.stream().map(order -> {
            OrderResponse res = new OrderResponse();
            res.setId(order.getId());
            res.setTrackingNumber(order.getOrderTrackingNumber());
            res.setTotalAmount(order.getTotalAmount());
            res.setStatus(order.getStatus());
            res.setDateCreated(order.getDateCreated());
            
            // Map items inside the order
            List<OrderItemDTO> itemDtos = order.getOrderItems().stream().map(item -> {
                OrderItemDTO dto = new OrderItemDTO();
                dto.setProductName(item.getProduct().getName());
                dto.setQuantity(item.getQuantity());
                dto.setPriceAtPurchase(item.getPriceAtPurchase());
                return dto;
            }).collect(Collectors.toList());
            
            res.setItems(itemDtos);
            return res;
        }).collect(Collectors.toList());
    }
}