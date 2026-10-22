package ar.edu.itba.sale.application.port.out;

import ar.edu.itba.sale.application.core.domain.Sale;

public interface SaveSaleOutputPort {

    Sale execute(Sale sale);
}
