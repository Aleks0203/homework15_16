package ru.mail.druk_aleksandr;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionRepository {
    Connection getConnection() throws SQLException;
}
