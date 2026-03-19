package ai.tanuj.ecomerce.Model;

import java.util.List;

import ai.tanuj.ecomerce.Entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
public class CartResponse {
    private List<CartItemDTO> items;
    private Double grandTotal;
}