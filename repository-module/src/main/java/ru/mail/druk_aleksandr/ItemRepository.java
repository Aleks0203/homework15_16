package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemRepository extends GeneralRepository<Item> {
    List<Integer> getIdItems(Connection connection) throws SQLException;

    void delete(Connection connection, int id) throws SQLException;
}
