package thanhnt.ec.ecsb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import thanhnt.ec.ecsb.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
