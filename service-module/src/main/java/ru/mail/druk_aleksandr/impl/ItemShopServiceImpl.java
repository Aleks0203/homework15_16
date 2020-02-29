package ru.mail.druk_aleksandr.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.druk_aleksandr.ConnectionRepository;
import ru.mail.druk_aleksandr.ItemShopRepository;
import ru.mail.druk_aleksandr.ItemShopService;
import ru.mail.druk_aleksandr.model.ItemShop;
import ru.mail.druk_aleksandr.model.ItemShopDTO;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;

@Service
public class ItemShopServiceImpl implements ItemShopService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository;
    private ItemShopRepository itemShopRepository;

    public ItemShopServiceImpl(ConnectionRepository connectionRepository,
                               ItemShopRepository itemShopRepository) {
        this.connectionRepository = connectionRepository;
        this.itemShopRepository = itemShopRepository;
    }

    @Override
    public void addItemShop(ItemShopDTO itemShopDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                ItemShop itemShop = convertDTOToItemShop(itemShopDTO);
                itemShopRepository.add(connection, itemShop);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Set<Integer> getItemsIds() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Set<Integer> itemsIds = itemShopRepository.getItemsId(connection);
                connection.commit();
                return itemsIds;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptySet();
    }

    private ItemShop convertDTOToItemShop(ItemShopDTO itemShopDTO) {
        ItemShop itemShop = new ItemShop();
        itemShop.setItemsId(itemShopDTO.getItemsId());
        itemShop.setShopsId(itemShopDTO.getShopsId());
        return itemShop;
    }
}
