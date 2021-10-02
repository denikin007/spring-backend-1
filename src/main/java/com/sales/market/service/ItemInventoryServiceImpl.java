package com.sales.market.service;

import com.sales.market.model.ItemInstanceStatus;
import com.sales.market.model.ItemInventory;
import com.sales.market.model.ItemInventoryEntry;
import com.sales.market.model.MovementType;
import com.sales.market.repository.GenericRepository;
import com.sales.market.repository.ItemInventoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemInventoryServiceImpl extends GenericServiceImpl<ItemInventory> implements ItemInventoryService{

    private final ItemInventoryRepository repository;
    private final ItemInstanceService itemInstanceService;

    public ItemInventoryServiceImpl(ItemInventoryRepository repository, ItemInstanceService itemInstanceService){
        this.repository = repository;
        this.itemInstanceService = itemInstanceService;
    }

    @Override
    protected GenericRepository<ItemInventory> getRepository() {
        return repository;
    }

    @Override
    public ItemInventory updateInventory(ItemInventoryEntry itemInventoryEntry) {
        ItemInventory itemInventory = itemInventoryEntry.getItemInventory();
        MovementType movementType = itemInventoryEntry.getMovementType();
        BigDecimal quantity = itemInventoryEntry.getQuantity();
        String [] identifiers = itemInventoryEntry.getItemInstanceSkus().split(",");

        switch (movementType) {
            case BUY:
                itemInventory.setStockQuantity(itemInventory.getStockQuantity().add(quantity));
                itemInstanceService.saveAllByIdentifiers(identifiers, itemInventory);
                break;
            case SALE:
                itemInventory.setStockQuantity(itemInventory.getStockQuantity().subtract(quantity));
                itemInstanceService.setState(identifiers, ItemInstanceStatus.SOLD);
                break;
            case REMOVED:
                itemInventory.setStockQuantity(itemInventory.getStockQuantity().subtract(quantity));
                itemInstanceService.setState(identifiers, ItemInstanceStatus.SCREWED);
                break;
        }
        return save(itemInventory);
    }
}
