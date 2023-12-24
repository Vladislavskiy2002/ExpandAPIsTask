package com.vladislavskiy.ExpandAPIsTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladislavskiy.ExpandAPIsTask.dto.AuthRequest;
import com.vladislavskiy.ExpandAPIsTask.dto.ProductRequest;
import com.vladislavskiy.ExpandAPIsTask.model.Product;
import com.vladislavskiy.ExpandAPIsTask.service.ProductService;
import com.vladislavskiy.ExpandAPIsTask.service.impl.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @MockBean
    private ProductService productService;


    @Test
    public void testAddProducts() throws Exception {
        // Given
        ProductRequest productRequest = createSampleProductRequest();

        // Generate Token
        String token = generateToken("name1", "2134");
        System.out.println(token);

        // When
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products/add")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        verify(productService, times(1)).saveRecords(eq(productRequest.getTable()), anyList());
    }

    private String generateToken(String username, String password) {
        // Use your AuthController to generate a token
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(username);
        authRequest.setPassword(password);

        // Perform authentication
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        // Set the Authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate Token
        return jwtService.generateToken(authRequest.getUsername());
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
        record1.setItemQuantity(2340);
        record1.setStatus("Paid");

        Product record2 = new Product();
        record2.setEntryDate("03-01-2023");
        record2.setItemCode("11112");
        record2.setItemName("Test Inventory 2");
        record2.setItemQuantity(20);
        record2.setStatus("Paid");

        request.setRecords(Arrays.asList(record1, record2));

        return request;
    }
}
