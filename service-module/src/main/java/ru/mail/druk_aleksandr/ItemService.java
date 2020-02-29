package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.ItemDTO;

import java.util.List;

public interface ItemService {
    void addItem(ItemDTO itemDTO);

    List<Integer> getIdItems();

    void deleteItemById(int userId);
}
