package ar.edu.itba.sale.application.port.out;

import ar.edu.itba.sale.application.core.domain.Sale;

import java.util.Optional;

public interface FindSaleByIdOutputPort {

    Optional<Sale> execute(Long id);
}
