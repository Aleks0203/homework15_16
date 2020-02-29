package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.ShopDTO;

import java.util.List;

public interface ShopService {
    void addShop(ShopDTO shopDTO);

    List<Integer> getIdShops();
}
