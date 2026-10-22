package ar.edu.itba.sale.adapters.out.repository;

import ar.edu.itba.sale.adapters.out.repository.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
}
