package ai.tanuj.ecomerce.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Version
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long version = 0L;

    @Column(columnDefinition = "TEXT") // Good for long descriptions
    private String description;

    @Column(nullable = false)
    private Double price;

    // Setting the default value at the database level
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer stockQuantity = 0;
    
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private UserEntity vendor;
}