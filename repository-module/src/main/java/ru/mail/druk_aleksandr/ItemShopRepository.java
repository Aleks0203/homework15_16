package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.ItemShop;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface ItemShopRepository extends GeneralRepository<ItemShop> {
    Set<Integer> getItemsId(Connection connection) throws SQLException;

    void delete(Connection connection, int id) throws SQLException;
}
