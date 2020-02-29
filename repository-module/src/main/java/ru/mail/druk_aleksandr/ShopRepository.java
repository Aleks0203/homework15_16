package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.Shop;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ShopRepository extends GeneralRepository<Shop> {
    List<Integer> getIdShops(Connection connection) throws SQLException;
}
