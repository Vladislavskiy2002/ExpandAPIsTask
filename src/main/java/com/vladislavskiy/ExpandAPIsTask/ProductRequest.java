package com.vladislavskiy.ExpandAPIsTask;

import com.vladislavskiy.ExpandAPIsTask.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequest {
    private String table;
    private List<Product> records;
}
