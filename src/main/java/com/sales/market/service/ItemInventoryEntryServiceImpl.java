package com.sales.market.service;

import com.sales.market.model.ItemInstanceStatus;
import com.sales.market.model.ItemInventory;
import com.sales.market.model.ItemInventoryEntry;
import com.sales.market.model.MovementType;
import com.sales.market.repository.GenericRepository;
import com.sales.market.repository.ItemInventoryEntryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemInventoryEntryServiceImpl extends GenericServiceImpl<ItemInventoryEntry> implements ItemInventoryEntryService{
    private final ItemInventoryEntryRepository repository;
    private final ItemInventoryService itemInventoryService;

    public ItemInventoryEntryServiceImpl(ItemInventoryEntryRepository repository, ItemInventoryService itemInventoryService){
        this.repository = repository;
        this.itemInventoryService = itemInventoryService;
    }

    @Override
    protected GenericRepository<ItemInventoryEntry> getRepository() {
        return repository;
    }

    @Override
    public ItemInventoryEntry save(ItemInventoryEntry itemInventoryEntry) {
        itemInventoryService.updateInventory(itemInventoryEntry);
        return repository.save(itemInventoryEntry);
    }
}
