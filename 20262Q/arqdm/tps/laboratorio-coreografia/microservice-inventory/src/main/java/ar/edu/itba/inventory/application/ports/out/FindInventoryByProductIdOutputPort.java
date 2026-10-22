package ar.edu.itba.inventory.application.ports.out;

import ar.edu.itba.inventory.application.core.domain.Inventory;

import java.util.Optional;

public interface FindInventoryByProductIdOutputPort {

    Optional<Inventory> execute(Long productId);
}
