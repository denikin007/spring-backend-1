package com.sales.market.controller;

import com.sales.market.dto.ItemInventoryEntryDto;
import com.sales.market.model.ItemInventoryEntry;
import com.sales.market.service.GenericService;
import com.sales.market.service.ItemInventoryEntryService;
import com.sales.market.service.ItemInventoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item-inventory-entry")
public class ItemInventoryEntryController extends GenericController<ItemInventoryEntry, ItemInventoryEntryDto> {
    private final ItemInventoryEntryService service;

    ItemInventoryEntryController(ItemInventoryEntryService service){
        this.service = service;
    }
    @Override
    protected GenericService getService() {return service;}
}
