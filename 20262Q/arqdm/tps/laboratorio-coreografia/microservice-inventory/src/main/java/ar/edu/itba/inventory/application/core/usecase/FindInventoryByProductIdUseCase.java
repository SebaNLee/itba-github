package ar.edu.itba.inventory.application.core.usecase;

import ar.edu.itba.inventory.application.core.domain.Inventory;
import ar.edu.itba.inventory.application.ports.in.FindInventoryByProductIdInputPort;
import ar.edu.itba.inventory.application.ports.out.FindInventoryByProductIdOutputPort;

public class FindInventoryByProductIdUseCase implements FindInventoryByProductIdInputPort {

    private final FindInventoryByProductIdOutputPort findInventoryByProductIdOutputPort;

    public FindInventoryByProductIdUseCase(final FindInventoryByProductIdOutputPort findInventoryByProductIdOutputPort) {
        this.findInventoryByProductIdOutputPort = findInventoryByProductIdOutputPort;
    }

    @Override
    public Inventory execute(final Long productId) {
        return this.findInventoryByProductIdOutputPort.execute(productId)
                .orElseThrow(() -> new RuntimeException("estoque não encontrado para esse produto"));
    }
}
