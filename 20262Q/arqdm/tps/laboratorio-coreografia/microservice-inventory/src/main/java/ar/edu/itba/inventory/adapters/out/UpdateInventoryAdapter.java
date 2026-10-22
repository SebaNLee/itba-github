package ar.edu.itba.inventory.adapters.out;

import ar.edu.itba.inventory.adapters.out.repository.InventoryRepository;
import ar.edu.itba.inventory.adapters.out.repository.mapper.InventoryEntityMapper;
import ar.edu.itba.inventory.application.core.domain.Inventory;
import ar.edu.itba.inventory.application.ports.out.UpdateInventoryOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateInventoryAdapter implements UpdateInventoryOutputPort {

    private final InventoryRepository inventoryRepository;
    private final InventoryEntityMapper inventoryEntityMapper;

    @Override
    public void execute(final Inventory inventory) {
        final var inventoryEntity = this.inventoryEntityMapper.inventoryToInventoryEntity(inventory);
        this.inventoryRepository.saveAndFlush(inventoryEntity);
    }
}
