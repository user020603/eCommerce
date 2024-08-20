package thanhnt.ec.ecsb.repositories;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import thanhnt.ec.ecsb.model.Product;

import java.awt.print.Pageable;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existByName(String name);
    Page<Product> findAll(Pageable pageable);
}
