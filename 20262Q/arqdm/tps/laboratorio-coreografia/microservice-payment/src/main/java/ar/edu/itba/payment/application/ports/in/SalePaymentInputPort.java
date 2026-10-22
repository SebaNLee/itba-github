package ar.edu.itba.payment.application.ports.in;

import ar.edu.itba.payment.application.core.domain.Sale;

public interface SalePaymentInputPort {

    void execute(Sale sale);
}
