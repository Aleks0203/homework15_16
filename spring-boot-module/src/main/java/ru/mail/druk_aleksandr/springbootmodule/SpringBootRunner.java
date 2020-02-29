package ru.mail.druk_aleksandr.springbootmodule;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.mail.druk_aleksandr.ItemService;
import ru.mail.druk_aleksandr.ItemShopService;
import ru.mail.druk_aleksandr.ShopService;
import ru.mail.druk_aleksandr.model.ItemDTO;
import ru.mail.druk_aleksandr.model.ItemShopDTO;
import ru.mail.druk_aleksandr.model.ShopDTO;

import java.util.List;
import java.util.Set;

@Component
public class SpringBootRunner implements ApplicationRunner {
    private ItemService itemService;
    private ShopService shopService;
    private ItemShopService itemShopService;

    public SpringBootRunner(ItemService itemService,
                            ShopService shopService,
                            ItemShopService itemShopService) {
        this.itemService = itemService;
        this.shopService = shopService;
        this.itemShopService = itemShopService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("Item");
        itemDTO.setDescription("Description");
        itemDTO.setPrice(100);
        itemService.addItem(itemDTO);

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setName("Shop");
        shopDTO.setLocation("https://");
        shopService.addShop(shopDTO);

        List<Integer> idItems = itemService.getIdItems();
        List<Integer> idShops = shopService.getIdShops();
        ItemShopDTO itemShopDTO = new ItemShopDTO();
        for (Integer idtem : idItems) {
            itemShopDTO.setItemsId(idtem);
            itemShopDTO.setShopsId(idShops.get((int) (Math.random() * idShops.size())));
            itemShopService.addItemShop(itemShopDTO);
        }

        Set<Integer> itemsIds = itemShopService.getItemsIds();
        for (Integer idItem : idItems) {
            for (Integer itemsId : itemsIds) {
                if (idItem == itemsId) {
                    itemService.deleteItemById(idItem);
                }
            }
        }
    }
}
