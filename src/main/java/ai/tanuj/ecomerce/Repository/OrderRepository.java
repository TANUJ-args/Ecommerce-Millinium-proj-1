package ai.tanuj.ecomerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.tanuj.ecomerce.Entity.OrderEntity;
import ai.tanuj.ecomerce.Entity.UserEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserOrderByDateCreatedDesc(UserEntity user);
    
}
