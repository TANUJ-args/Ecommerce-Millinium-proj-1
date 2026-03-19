package ai.tanuj.ecomerce.Model;
import lombok.Data;
@Data
public class CartItemDTO {
    private Long productId;
    private String productName;
    private String productDescription;
    private Double unitPrice;
    private Integer quantity;
    private Double subTotal;
}