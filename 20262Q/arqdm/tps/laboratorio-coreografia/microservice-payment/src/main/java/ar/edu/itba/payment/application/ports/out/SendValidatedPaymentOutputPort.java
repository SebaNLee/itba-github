package ar.edu.itba.payment.application.ports.out;

import ar.edu.itba.payment.application.core.domain.Sale;
import ar.edu.itba.payment.application.core.domain.enums.SaleEventEnum;

public interface SendValidatedPaymentOutputPort {

    void execute(Sale sale, SaleEventEnum saleEventEnum);
}
