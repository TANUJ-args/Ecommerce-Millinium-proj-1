package ai.tanuj.ecomerce.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    
    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotBlank(message = "Please provide a description")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stockQuantity = 0; // Defaulting to 0 here
}