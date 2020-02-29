package ru.mail.druk_aleksandr;

import java.sql.Connection;
import java.sql.SQLException;

public interface GeneralRepository<T> {
    void add(Connection connection, T t) throws SQLException;
}
