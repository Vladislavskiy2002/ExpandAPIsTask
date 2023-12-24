package com.vladislavskiy.ExpandAPIsTask.service;

import com.vladislavskiy.ExpandAPIsTask.model.Product;

import java.util.List;

public interface ProductService {
    void saveRecords(String tableName, List<Product> records);
}
