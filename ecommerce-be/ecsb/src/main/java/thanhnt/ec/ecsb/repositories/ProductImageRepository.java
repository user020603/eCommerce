package thanhnt.ec.ecsb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import thanhnt.ec.ecsb.model.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}
