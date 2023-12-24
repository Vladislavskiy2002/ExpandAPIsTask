package com.vladislavskiy.ExpandAPIsTask.service;

import com.vladislavskiy.ExpandAPIsTask.model.Product;
import com.vladislavskiy.ExpandAPIsTask.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveRecords(String tableName, List<Product> records) {
        createTableIfNotExists(tableName);
        productRepository.saveAll(records);
    }

    private void createTableIfNotExists(String tableName) {
        if (!isTableExists(tableName)) {
            String createTableQuery = "CREATE TABLE " + tableName + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "itemCode VARCHAR(255) ," +
                    "entryDate VARCHAR(255)," +
                    "itemName VARCHAR(255)," +
                    "itemQuantity INT," +
                    "status VARCHAR(255))";

            entityManager.createNativeQuery(createTableQuery).executeUpdate();
        }
    }

    private boolean isTableExists(String tableName) {
        return entityManager.createNativeQuery(
                "SELECT 1 FROM information_schema.tables WHERE table_name = :tableName")
                .setParameter("tableName", tableName)
                .getResultList().size() > 0;
    }
}
