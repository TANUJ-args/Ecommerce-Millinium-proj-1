package ai.tanuj.ecomerce.Controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import ai.tanuj.ecomerce.Model.Product;
import ai.tanuj.ecomerce.Service.VendorService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping; 


@PreAuthorize("hasRole('VENDOR')")
@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService; 

   @PostMapping("/addproduct")
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product, Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(vendorService.addProduct(product, email));
    }

    @GetMapping("/myproducts")
    public List<Product> myproducts(Authentication authentication) {
        String email = authentication.getName();
        return vendorService.MyProducts(null, email);
    }
    
}
    
