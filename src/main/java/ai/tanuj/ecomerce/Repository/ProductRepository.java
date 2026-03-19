package ai.tanuj.ecomerce.Repository;

import ai.tanuj.ecomerce.Entity.ProductEntity;
import ai.tanuj.ecomerce.Entity.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByVendor(UserEntity vendor);
}
