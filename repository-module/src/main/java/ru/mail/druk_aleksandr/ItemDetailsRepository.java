package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.ItemDetails;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDetailsRepository extends GeneralRepository<ItemDetails> {
    void delete(Connection connection, int id) throws SQLException;
}
