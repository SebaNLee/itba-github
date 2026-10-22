package ar.edu.itba.inventory.application.core.usecase;

import ar.edu.itba.inventory.application.core.domain.Inventory;
import ar.edu.itba.inventory.application.core.domain.Sale;
import ar.edu.itba.inventory.application.core.domain.enums.SaleEventEnum;
import ar.edu.itba.inventory.application.ports.in.DebitInventoryInputPort;
import ar.edu.itba.inventory.application.ports.in.FindInventoryByProductIdInputPort;
import ar.edu.itba.inventory.application.ports.out.SendDebitInventoryOutputPort;
import ar.edu.itba.inventory.application.ports.out.SendFailedInventoryOutputPort;
import ar.edu.itba.inventory.application.ports.out.UpdateInventoryOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebitInventoryUseCase implements DebitInventoryInputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebitInventoryUseCase.class);

    private final FindInventoryByProductIdInputPort findInventoryByProductIdInputPort;
    private final UpdateInventoryOutputPort updateInventoryOutputPort;
    private final SendDebitInventoryOutputPort sendDebitInventoryOutputPort;
    private final SendFailedInventoryOutputPort sendFailedInventoryOutputPort;

    public DebitInventoryUseCase(final FindInventoryByProductIdInputPort findInventoryByProductIdInputPort,
                                 final UpdateInventoryOutputPort updateInventoryOutputPort,
                                 final SendDebitInventoryOutputPort sendDebitInventoryOutputPort,
                                 final SendFailedInventoryOutputPort sendFailedInventoryOutputPort) {

        this.findInventoryByProductIdInputPort = findInventoryByProductIdInputPort;
        this.updateInventoryOutputPort = updateInventoryOutputPort;
        this.sendDebitInventoryOutputPort = sendDebitInventoryOutputPort;
        this.sendFailedInventoryOutputPort = sendFailedInventoryOutputPort;
    }

    @Override
    public void execute(final Sale sale) {
        try {
            final var inventory = this.findInventoryByProductIdInputPort.execute(sale.getProductId());
            this.checkQuantityInInventory(inventory, sale.getQuantity());

            inventory.debitQuantity(sale.getQuantity());
            this.updateInventoryOutputPort.execute(inventory);

            this.sendDebitInventoryOutputPort.execute(sale, SaleEventEnum.UPDATED_INVENTORY);
        } catch (final Exception ex) {
            LOGGER.error("DebitInventoryUseCase - error [{}]", ex.getMessage());
            this.sendFailedInventoryOutputPort.execute(sale, SaleEventEnum.ROLLBACK_INVENTORY);
        }
    }

    private void checkQuantityInInventory(final Inventory inventory, final int saleQuantity) {
        if (inventory.getQuantity() < saleQuantity) throw new RuntimeException("Out of stock");
    }
}
