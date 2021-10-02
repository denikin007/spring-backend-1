/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market;

import com.sales.market.model.*;
import com.sales.market.repository.BuyRepository;
import com.sales.market.service.*;
import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Component
public class DevelopmentBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final BuyRepository buyRepository;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final ItemService itemService;
    private final ItemInstanceService itemInstanceService;
    private final ItemInventoryService itemInventoryService;
    private final ItemInventoryEntryService itemInventoryEntryService;

    SubCategory beverageSubCat = null;

    // injeccion evita hacer instancia   = new Clase();
    // bean pueden tener muchos campos y otros beans asociados

    public DevelopmentBootstrap(BuyRepository buyRepository, CategoryService categoryService,
                                SubCategoryService subCategoryService, ItemService itemService,
                                ItemInstanceService itemInstanceService, ItemInventoryService itemInventoryService, ItemInventoryEntryService itemInventoryEntryService) {
        this.buyRepository = buyRepository;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.itemService = itemService;
        this.itemInstanceService = itemInstanceService;
        this.itemInventoryService = itemInventoryService;
        this.itemInventoryEntryService = itemInventoryEntryService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("evento de spring");
        /*   duplicacion de codigo
        Buy buy = new Buy();
        buy.setValue(BigDecimal.TEN);
        buyRespository.save(buy);
        Buy buy2 = new Buy();
        buy2.setValue(BigDecimal.ONE);
        buyRespository.save(buy);*/

        persistBuy(BigDecimal.TEN);
        persistBuy(BigDecimal.ONE);
        persistCategoriesAndSubCategories();
        Item maltinItem = persistItems(beverageSubCat);
        //persistItemInstances(maltinItem);
        ItemInventory itemInventory = new ItemInventory();
        itemInventory.setItem(maltinItem);
        itemInventory.setUpperBoundThreshold(BigDecimal.valueOf(20));
        itemInventory.setLowerBoundThreshold(BigDecimal.valueOf(5));
        itemInventory.setStockQuantity(BigDecimal.valueOf(0));
        itemInventory.setTotalPrice(itemInventory.getStockQuantity().multiply(BigDecimal.valueOf(5)));
        ItemInventory itemInventory1 = itemInventoryService.save(itemInventory);
        ItemInventoryEntry itemInventoryEntry = new ItemInventoryEntry();
        itemInventoryEntry.setItemInstanceSkus("SKU-77721106006158,SKU-77721106006159,SKU-77721106006160");
        itemInventoryEntry.setItemInventory(itemInventory1);
        itemInventoryEntry.setQuantity(BigDecimal.valueOf(3));
        itemInventoryEntry.setMovementType(MovementType.BUY);
        itemInventoryEntryService.save(itemInventoryEntry);
    }

    private void persistItemInstances(Item maltinItem) {
        ItemInstance maltinItem1 = createItem(maltinItem, "SKU-77721106006158", ItemInstanceStatus.AVAILABLE,5D);
        ItemInstance maltinItem2 = createItem(maltinItem, "SKU-77721106006159", ItemInstanceStatus.AVAILABLE,5D);
        ItemInstance maltinItem3 = createItem(maltinItem, "SKU-77721106006160", ItemInstanceStatus.SCREWED, 5D);
        ItemInstance maltinItem4 = createItem(maltinItem, "SKU-77721106006161", ItemInstanceStatus.SOLD,5D);
        itemInstanceService.save(maltinItem1);
        itemInstanceService.save(maltinItem2);
        itemInstanceService.save(maltinItem3);
        itemInstanceService.save(maltinItem4);
    }

    private ItemInstance createItem(Item maltinItem, String sku, ItemInstanceStatus status,double price) {
        ItemInstance itemInstance = new ItemInstance();
        itemInstance.setItem(maltinItem);
        itemInstance.setFeatured(true);
        itemInstance.setPrice(price);
        itemInstance.setIdentifier(sku);
        itemInstance.setItemInstanceStatus(status);
        return itemInstance;
    }

    private Item persistItems(SubCategory subCategory) {
        Item item = new Item();
        item.setCode("B-MALTIN");
        item.setName("MALTIN");
        item.setSubCategory(subCategory);
        /*try {
            item.setImage(ImageUtils.inputStreamToByteArray(getResourceAsStream("/images/maltin.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return itemService.save(item);
    }

    private String getResourceAsString(String resourceName) {
        try (InputStream inputStream = this.getClass().getResourceAsStream(resourceName)) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private InputStream getResourceAsStream(String resourceName) {
        try (InputStream inputStream = this.getClass().getResourceAsStream(resourceName)) {
            return inputStream;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void persistCategoriesAndSubCategories() {
        Category category = persistCategory();
        persistSubCategory("SUBCAT1-NAME", "SUBCAT1-CODE", category);
        beverageSubCat = persistSubCategory("BEVERAGE", "BEVERAGE-CODE", category);
    }

    private Category persistCategory() {
        Category category = new Category();
        category.setName("CAT1-NAME");
        category.setCode("CAT1-CODE");
        return categoryService.save(category);
    }

    private SubCategory persistSubCategory(String name, String code, Category category) {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(name);
        subCategory.setCode(code);
        subCategory.setCategory(category);
        return subCategoryService.save(subCategory);
    }

    private void persistBuy(BigDecimal value) {
        Buy buy = new Buy();
        buy.setValue(value);
        buyRepository.save(buy);
    }

    private void persistenItemInventory(){
        ItemInventory itemInventory = new ItemInventory();
        Item item = itemService.findById(1L);
        itemInventory.setItem(item);
        itemInventory.setLowerBoundThreshold(BigDecimal.valueOf(5));
        itemInventory.setStockQuantity(BigDecimal.valueOf(10));
        itemInventory.setTotalPrice(BigDecimal.valueOf(100));
        itemInventory.setUpperBoundThreshold(BigDecimal.valueOf(20));
        itemInventoryService.save(itemInventory);
    }
}
