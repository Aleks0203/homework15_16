package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.ItemShopDTO;

import java.util.Set;

public interface ItemShopService {
    void addItemShop(ItemShopDTO itemShopDTO);

    Set<Integer> getItemsIds();
}
