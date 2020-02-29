package ru.mail.druk_aleksandr.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.druk_aleksandr.*;
import ru.mail.druk_aleksandr.model.Item;
import ru.mail.druk_aleksandr.model.ItemDTO;
import ru.mail.druk_aleksandr.model.ItemDetails;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository;
    private ItemRepository itemRepository;
    private ItemDetailsRepository itemDetailsRepository;
    private ItemShopRepository itemShopRepository;

    public ItemServiceImpl(ConnectionRepository connectionRepository,
                           ItemRepository itemRepository,
                           ItemDetailsRepository itemDetailsRepository,
                           ItemShopRepository itemShopRepository) {
        this.connectionRepository = connectionRepository;
        this.itemRepository = itemRepository;
        this.itemDetailsRepository = itemDetailsRepository;
        this.itemShopRepository = itemShopRepository;
    }

    @Override
    public void addItem(ItemDTO itemDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convertDTOToItem(itemDTO);
                itemRepository.add(connection, item);
                item.getItemDetails().setItemId(item.getId());
                itemDetailsRepository.add(connection, item.getItemDetails());
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
    public List<Integer> getIdItems() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Integer> idItems = itemRepository.getIdItems(connection);
                connection.commit();
                return idItems;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteItemById(int id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                itemShopRepository.delete(connection, id);
                itemDetailsRepository.delete(connection, id);
                itemRepository.delete(connection, id);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Item convertDTOToItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());

        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setPrice(itemDTO.getPrice());
        item.setItemDetails(itemDetails);
        return item;
    }
}
