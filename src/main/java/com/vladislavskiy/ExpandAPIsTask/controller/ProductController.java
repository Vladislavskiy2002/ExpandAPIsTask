package com.vladislavskiy.ExpandAPIsTask.controller;

import com.vladislavskiy.ExpandAPIsTask.dto.ProductRequest;
import com.vladislavskiy.ExpandAPIsTask.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProducts(@RequestBody ProductRequest payload) {
        productService.saveRecords(payload.getTable(), payload.getRecords());
        return new ResponseEntity<>("Records saved successfully", HttpStatus.OK);
    }
}
