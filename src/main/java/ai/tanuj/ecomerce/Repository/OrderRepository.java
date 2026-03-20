package ai.tanuj.ecomerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.tanuj.ecomerce.Entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    
}
