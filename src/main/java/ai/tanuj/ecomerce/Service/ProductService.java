package ai.tanuj.ecomerce.Service;

import ai.tanuj.ecomerce.Entity.ProductEntity;
import java.util.List;

public interface ProductService {
    List<ProductEntity> getAllProducts();
}