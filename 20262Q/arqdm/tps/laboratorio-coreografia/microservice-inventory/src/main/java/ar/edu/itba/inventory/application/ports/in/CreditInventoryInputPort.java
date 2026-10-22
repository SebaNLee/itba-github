package ar.edu.itba.inventory.application.ports.in;

import ar.edu.itba.inventory.application.core.domain.Sale;

public interface CreditInventoryInputPort {

    void execute(Sale sale);
}
