package ar.edu.itba.payment.adapters.out.repository;

import ar.edu.itba.payment.adapters.out.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
