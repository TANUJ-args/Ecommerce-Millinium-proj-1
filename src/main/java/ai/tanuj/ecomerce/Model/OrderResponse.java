package ai.tanuj.ecomerce.Model;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
@Data
public class OrderResponse {
    private Long id;
    private String trackingNumber;
    private Double totalAmount;
    private String status;
    private LocalDateTime dateCreated;
    private List<CartItemDTO> items; // We can reuse a similar DTO structure here
}