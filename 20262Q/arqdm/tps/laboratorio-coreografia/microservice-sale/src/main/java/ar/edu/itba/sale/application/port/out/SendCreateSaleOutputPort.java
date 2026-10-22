package ar.edu.itba.sale.application.port.out;

import ar.edu.itba.sale.application.core.domain.Sale;
import ar.edu.itba.sale.application.core.domain.enums.SaleEventEnum;

public interface SendCreateSaleOutputPort {

    void execute(Sale sale, SaleEventEnum saleEventEnum);
}
