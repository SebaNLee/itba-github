package ar.edu.itba.inventory.application.ports.out;

import ar.edu.itba.inventory.application.core.domain.Inventory;

public interface UpdateInventoryOutputPort {

    void execute(Inventory inventory);
}
