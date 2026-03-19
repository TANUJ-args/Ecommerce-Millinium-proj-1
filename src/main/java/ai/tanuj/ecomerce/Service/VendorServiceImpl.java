package ai.tanuj.ecomerce.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.tanuj.ecomerce.Entity.ProductEntity;
import ai.tanuj.ecomerce.Entity.UserEntity;
import ai.tanuj.ecomerce.Model.Product;
import ai.tanuj.ecomerce.Repository.ProductRepository;
import ai.tanuj.ecomerce.Repository.UserRepository;
@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String addProduct(Product product, String vendorEmail) {
        UserEntity vendor = userRepository.findByEmail(vendorEmail)
                .orElseThrow(() -> new RuntimeException("Vendor not found!"));

        ProductEntity newProduct = new ProductEntity();
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setStockQuantity(product.getStockQuantity());
        newProduct.setVendor(vendor);

        productRepository.save(newProduct);

        return "Product successfully added to " + vendor.getEmail() + "!";
    }

    @Override
    public List<Product> MyProducts(UserEntity vendor, String vendorEmail) {
        UserEntity vendorUser = userRepository.findByEmail(vendorEmail)
                .orElseThrow(() -> new RuntimeException("Vendor not found!"));
        List<ProductEntity> productEntities = productRepository.findByVendor(vendorUser);
        List<Product> products = new ArrayList<>();
        for (ProductEntity productEntity : productEntities) {
            Product product = new Product();
            BeanUtils.copyProperties(productEntity, product);
            products.add(product);
        }
        return products;
    }
}