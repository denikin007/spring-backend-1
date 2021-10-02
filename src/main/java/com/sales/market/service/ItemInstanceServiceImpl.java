/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market.service;

import com.sales.market.model.Item;
import com.sales.market.model.ItemInstance;
import com.sales.market.model.ItemInstanceStatus;
import com.sales.market.model.ItemInventory;
import com.sales.market.repository.GenericRepository;
import com.sales.market.repository.ItemInstanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemInstanceServiceImpl extends GenericServiceImpl<ItemInstance> implements ItemInstanceService {
    private final ItemInstanceRepository repository;
    private final ItemService itemService;

    public ItemInstanceServiceImpl(ItemInstanceRepository repository, ItemService itemService) {
        this.repository = repository;
        this.itemService = itemService;
    }

    @Override
    protected GenericRepository<ItemInstance> getRepository() {
        return repository;
    }

    @Override
    public ItemInstance bunchSave(ItemInstance itemInstance) {
        // here make all objects save other than this resource
        if (itemInstance.getItem() != null) {
            // todo habria que distinguir si permitiremos guardar y  actualizar o ambos mitando el campo id
            itemService.save(itemInstance.getItem());
        }
        return super.bunchSave(itemInstance);
    }

    @Override
    public void saveAllByIdentifiers(String[] identifiers, ItemInventory itemInventory) {
        for (String identifier: identifiers) {
            ItemInstance itemInstance = new ItemInstance();
            itemInstance.setItem(itemInventory.getItem());
            itemInstance.setIdentifier(identifier);
            itemInstance.setPrice(5D);
            itemInstance.setItemInstanceStatus(ItemInstanceStatus.AVAILABLE);
            save(itemInstance);
            itemInventory.setTotalPrice(itemInventory.getTotalPrice().add(BigDecimal.valueOf(itemInstance.getPrice())));
        }
    }

    @Override
    public void setState(String[] identifiers, ItemInstanceStatus itemInstanceStatus) {
        for (String identifier : identifiers) {
            ItemInstance itemInstance = findByIdentifier(identifier);
            itemInstance.setItemInstanceStatus(itemInstanceStatus);
            save(itemInstance);
        }
    }

    @Override
    public ItemInstance findByIdentifier(String identifier){
        ItemInstance itemInstance = repository.findByIdentifier(identifier);
        return itemInstance;
    }

}
