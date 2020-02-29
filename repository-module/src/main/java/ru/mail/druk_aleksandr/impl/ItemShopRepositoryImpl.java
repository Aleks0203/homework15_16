package ru.mail.druk_aleksandr.impl;

import org.springframework.stereotype.Repository;
import ru.mail.druk_aleksandr.ItemShopRepository;
import ru.mail.druk_aleksandr.model.ItemShop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Repository
public class ItemShopRepositoryImpl extends GeneralRepositoryImpl<ItemShop> implements ItemShopRepository {
    @Override
    public void add(Connection connection, ItemShop itemShop) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO item_shop(items_id, shops_id) VALUES (?, ?)")) {
            preparedStatement.setInt(1, itemShop.getItemsId());
            preparedStatement.setInt(2, itemShop.getShopsId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting item_shop failed, no rows affected.");
            }
        }
    }

    @Override
    public Set<Integer> getItemsId(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT items_id FROM Item_Shop")) {
            Set<Integer> itemsIds = new HashSet<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int itemsId = getId(resultSet);
                    itemsIds.add(itemsId);
                }
                return itemsIds;
            }
        }
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM item_shop WHERE items_id=?")) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting item_shop failed, no rows affected.");
            }
        }
    }

    private int getId(ResultSet rs) throws SQLException {
        int itemsId = rs.getInt("items_id");
        return itemsId;
    }
}
