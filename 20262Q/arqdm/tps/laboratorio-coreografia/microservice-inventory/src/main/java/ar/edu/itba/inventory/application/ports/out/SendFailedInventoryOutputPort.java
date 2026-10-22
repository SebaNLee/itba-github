package ar.edu.itba.inventory.application.ports.out;

import ar.edu.itba.inventory.application.core.domain.Sale;
import ar.edu.itba.inventory.application.core.domain.enums.SaleEventEnum;

public interface SendFailedInventoryOutputPort {

    void execute(Sale sale, SaleEventEnum saleEventEnum);

}
