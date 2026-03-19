package ai.tanuj.ecomerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ai.tanuj.ecomerce.Entity.CartItem;
import ai.tanuj.ecomerce.Entity.ProductEntity;
import ai.tanuj.ecomerce.Entity.UserEntity;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByUser(UserEntity user);
    
    Optional<CartItem> findByUserAndProduct(UserEntity user, ProductEntity product);
    
    void deleteByUser(UserEntity user);
}