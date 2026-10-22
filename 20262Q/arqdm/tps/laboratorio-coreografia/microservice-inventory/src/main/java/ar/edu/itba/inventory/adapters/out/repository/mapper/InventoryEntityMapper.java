package ar.edu.itba.inventory.adapters.out.repository.mapper;

import ar.edu.itba.inventory.adapters.out.repository.entity.InventoryEntity;
import ar.edu.itba.inventory.application.core.domain.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryEntityMapper {

    Inventory inventoryEntityToInventory(InventoryEntity inventoryEntity);

    InventoryEntity inventoryToInventoryEntity(Inventory inventory);

}
