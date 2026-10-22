package ar.edu.itba.inventory.application.core.usecase;

import ar.edu.itba.inventory.application.core.domain.Sale;
import ar.edu.itba.inventory.application.core.domain.enums.SaleEventEnum;
import ar.edu.itba.inventory.application.ports.in.CreditInventoryInputPort;
import ar.edu.itba.inventory.application.ports.in.FindInventoryByProductIdInputPort;
import ar.edu.itba.inventory.application.ports.out.SendCreditInventoryOutputPort;
import ar.edu.itba.inventory.application.ports.out.UpdateInventoryOutputPort;

public class CreditInventoryUseCase implements CreditInventoryInputPort {

    private final FindInventoryByProductIdInputPort findInventoryByProductIdInputPort;

    private final UpdateInventoryOutputPort updateInventoryOutputPort;

    private final SendCreditInventoryOutputPort sendCreditInventoryOutputPort;

    public CreditInventoryUseCase(final FindInventoryByProductIdInputPort findInventoryByProductIdInputPort,
                                  final UpdateInventoryOutputPort updateInventoryOutputPort,
                                  final SendCreditInventoryOutputPort sendCreditInventoryOutputPort) {

        this.findInventoryByProductIdInputPort = findInventoryByProductIdInputPort;
        this.updateInventoryOutputPort = updateInventoryOutputPort;
        this.sendCreditInventoryOutputPort = sendCreditInventoryOutputPort;
    }

    @Override
    public void execute(final Sale sale) {
        var inventory = this.findInventoryByProductIdInputPort.execute(sale.getProductId());
        inventory.creditQuantity(sale.getQuantity());

        this.updateInventoryOutputPort.execute(inventory);
        this.sendCreditInventoryOutputPort.execute(sale, SaleEventEnum.ROLLBACK_INVENTORY);
    }
}
