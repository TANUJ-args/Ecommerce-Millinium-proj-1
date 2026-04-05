package ai.tanuj.ecomerce.Service;

import ai.tanuj.ecomerce.Entity.ProductEntity;
import ai.tanuj.ecomerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductEntity> getAllProducts() {
        System.out.println("Fetching from TiDB Database...");
        return productRepository.findAll();
    }


}