package ai.tanuj.ecomerce.Model;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String ProductName;
    private Integer Quantity;
    private Double PriceAtPurchase;
}
