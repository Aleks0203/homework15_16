package ru.mail.druk_aleksandr.impl;

import org.springframework.stereotype.Repository;
import ru.mail.druk_aleksandr.ItemDetailsRepository;
import ru.mail.druk_aleksandr.model.ItemDetails;

import java.sql.*;

@Repository
public class ItemDetailsRepositoryImpl extends GeneralRepositoryImpl<ItemDetails> implements ItemDetailsRepository {
    @Override
    public void add(Connection connection, ItemDetails itemDetails) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO itemdetails(item_id, price) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, itemDetails.getItemId());
            statement.setInt(2, itemDetails.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating itemDetails failed, no rows affected.");
            }
        }
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM itemdetails WHERE item_id=?")) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting itemDetalis failed, no rows affected.");
            }
        }
    }
}
