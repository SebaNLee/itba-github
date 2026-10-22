package ar.edu.itba.sale.application.port.in;

import ar.edu.itba.sale.application.core.domain.Sale;

public interface CreateSaleInputPort {

    void execute(Sale sale);
}
