/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market.repository;


import com.sales.market.model.ItemInstance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemInstanceRepository extends GenericRepository<ItemInstance> {

    @Query("from ItemInstance it where it.identifier=:identifier")
    ItemInstance findByIdentifier(@Param("identifier") String identifier);
}
