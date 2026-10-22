package ar.edu.itba.inventory.adapters.out;

import ar.edu.itba.inventory.adapters.out.repository.InventoryRepository;
import ar.edu.itba.inventory.adapters.out.repository.mapper.InventoryEntityMapper;
import ar.edu.itba.inventory.application.core.domain.Inventory;
import ar.edu.itba.inventory.application.ports.out.FindInventoryByProductIdOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindInventoryByProductIdAdapter implements FindInventoryByProductIdOutputPort {

    private final InventoryRepository inventoryRepository;
    private final InventoryEntityMapper inventoryEntityMapper;

    @Override
    public Optional<Inventory> execute(final Long productId) {
        final var inventoryEntity = this.inventoryRepository.findByProductId(productId);
        return inventoryEntity.map(this.inventoryEntityMapper::inventoryEntityToInventory);
    }
}
