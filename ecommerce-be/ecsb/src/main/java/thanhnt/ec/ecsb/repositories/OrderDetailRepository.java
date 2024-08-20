package thanhnt.ec.ecsb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import thanhnt.ec.ecsb.model.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);
}
