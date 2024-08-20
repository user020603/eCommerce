package thanhnt.ec.ecsb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import thanhnt.ec.ecsb.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
