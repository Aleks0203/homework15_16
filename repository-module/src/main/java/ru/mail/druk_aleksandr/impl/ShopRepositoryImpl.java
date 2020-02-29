package ru.mail.druk_aleksandr.impl;

import org.springframework.stereotype.Repository;
import ru.mail.druk_aleksandr.ShopRepository;
import ru.mail.druk_aleksandr.model.Shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShopRepositoryImpl extends GeneralRepositoryImpl<Shop> implements ShopRepository {
    @Override
    public void add(Connection connection, Shop shop) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO shop(name, location) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, shop.getName());
            statement.setString(2, shop.getLocation());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating shop failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    shop.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating shop failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Integer> getIdShops(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM shop")) {
            List<Integer> shopIds = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = getId(resultSet);
                    shopIds.add(id);
                }
                return shopIds;
            }
        }
    }

    private int getId(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        return id;
    }
}
