package ai.tanuj.ecomerce.Service;

import java.util.List;

import ai.tanuj.ecomerce.Model.OrderResponse;

public interface OrderService {
    String checkout(String email);
    List<OrderResponse> getOrderHistory(String email);
}
