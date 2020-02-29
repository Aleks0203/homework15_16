package ru.mail.druk_aleksandr.impl;

import org.springframework.stereotype.Repository;
import ru.mail.druk_aleksandr.ItemRepository;
import ru.mail.druk_aleksandr.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GeneralRepositoryImpl<Item> implements ItemRepository {
    @Override
    public void add(Connection connection, Item item) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO item(name, description) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Integer> getIdItems(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM item")) {
            List<Integer> itemIds = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = getId(resultSet);
                    itemIds.add(id);
                }
                return itemIds;
            }
        }
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM item WHERE id=?")) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting item failed, no rows affected.");
            }
        }
    }

    private int getId(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        return id;
    }
}
