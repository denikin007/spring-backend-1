/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market.service;

import com.sales.market.model.Item;
import com.sales.market.model.ItemInstance;
import com.sales.market.model.ItemInstanceStatus;
import com.sales.market.model.ItemInventory;

public interface ItemInstanceService extends GenericService<ItemInstance> {
    void saveAllByIdentifiers(String[] identifiers, ItemInventory itemInventory);
    void setState(String[] identifiers,ItemInstanceStatus itemInstanceStatus);
    ItemInstance findByIdentifier(String identifier);
}
