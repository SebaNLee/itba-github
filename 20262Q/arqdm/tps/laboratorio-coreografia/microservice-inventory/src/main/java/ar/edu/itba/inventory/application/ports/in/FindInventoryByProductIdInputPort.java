package ar.edu.itba.inventory.application.ports.in;

import ar.edu.itba.inventory.application.core.domain.Inventory;

public interface FindInventoryByProductIdInputPort {

    Inventory execute(Long productId);
}
