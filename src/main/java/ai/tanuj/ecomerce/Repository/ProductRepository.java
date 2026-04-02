package ai.tanuj.ecomerce.Repository;

import ai.tanuj.ecomerce.Entity.ProductEntity;
import ai.tanuj.ecomerce.Entity.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByVendor(UserEntity vendor);

    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET `version` = 0 WHERE `version` IS NULL", nativeQuery = true)
    int backfillNullVersions();
}
