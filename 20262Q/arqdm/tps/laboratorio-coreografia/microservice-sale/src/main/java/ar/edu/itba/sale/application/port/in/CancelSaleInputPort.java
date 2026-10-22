package ar.edu.itba.sale.application.port.in;

import ar.edu.itba.sale.application.core.domain.Sale;

public interface CancelSaleInputPort {

    void execute(final Sale sale);
}
