package com.vladislavskiy.ExpandAPIsTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladislavskiy.ExpandAPIsTask.controller.ProductController;
import com.vladislavskiy.ExpandAPIsTask.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddProductsEndpoint() throws Exception {
        // Prepare test data
        ProductRequest productRequest = new ProductRequest();
        productRequest.setTable("products");

        Product product1 = new Product();
        product1.setEntryDate("03-01-2023");
        product1.setItemCode("11111");
        product1.setItemName("Test Inventory 1");
        product1.setItemQuantity(20);
        product1.setStatus("Paid");

        productRequest.setRecords(Collections.singletonList(product1));

        // Convert to JSON
        String jsonPayload = objectMapper.writeValueAsString(productRequest);

        // Perform the request
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().string("Records saved successfully"))
                .andReturn();

        // Additional assertions if needed
    }
}