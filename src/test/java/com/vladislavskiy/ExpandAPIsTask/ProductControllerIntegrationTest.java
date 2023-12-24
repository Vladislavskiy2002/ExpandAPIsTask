package com.vladislavskiy.ExpandAPIsTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladislavskiy.ExpandAPIsTask.dto.ProductRequest;
import com.vladislavskiy.ExpandAPIsTask.model.Product;
import com.vladislavskiy.ExpandAPIsTask.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    public void testAddProducts() throws Exception {
        // Given
        ProductRequest productRequest = createSampleProductRequest();

        // When
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        // Then
        // Verify that productService.saveRecords was called once with the correct arguments
        verify(productService, times(1)).saveRecords(productRequest.getTable(), productRequest.getRecords());
    }

    private ProductRequest createSampleProductRequest() {
        // Create and return a sample ProductRequest for testing
        ProductRequest request = new ProductRequest();
        request.setTable("products");

        // Add sample records
        Product record1 = new Product();
        record1.setEntryDate("03-01-2023");
        record1.setItemCode("11111");
        record1.setItemName("Test Inventory 1");
        record1.setItemQuantity(20);
        record1.setStatus("Paid");

        Product record2 = new Product();
        record2.setEntryDate("03-01-2023");
        record2.setItemCode("11112");
        record2.setItemName("Test Inventory 2");
        record2.setItemQuantity(15);
        record2.setStatus("Unpaid");

        request.setRecords(Arrays.asList(record1, record2));

        return request;
    }
}
