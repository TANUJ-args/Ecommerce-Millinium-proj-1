package ai.tanuj.ecomerce.Service;

import java.util.List;

import ai.tanuj.ecomerce.Entity.UserEntity;
import ai.tanuj.ecomerce.Model.Product;

public interface VendorService {
    String addProduct(Product product, String vendorEmail);    
    List <Product> MyProducts(UserEntity vendor, String vendorEmail);
}