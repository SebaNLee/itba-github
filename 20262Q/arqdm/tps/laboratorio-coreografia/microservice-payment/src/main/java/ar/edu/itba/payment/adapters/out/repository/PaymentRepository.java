package ar.edu.itba.payment.adapters.out.repository;

import ar.edu.itba.payment.adapters.out.repository.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
