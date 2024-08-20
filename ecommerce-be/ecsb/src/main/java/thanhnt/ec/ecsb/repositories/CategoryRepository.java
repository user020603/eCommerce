package thanhnt.ec.ecsb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import thanhnt.ec.ecsb.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
