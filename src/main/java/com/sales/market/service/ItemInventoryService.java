package com.sales.market.service;

import com.sales.market.model.ItemInventory;
import com.sales.market.model.ItemInventoryEntry;

public interface ItemInventoryService extends GenericService<ItemInventory>{
    ItemInventory updateInventory(ItemInventoryEntry itemInventoryEntry);
}
