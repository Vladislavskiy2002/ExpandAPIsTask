package com.vladislavskiy.ExpandAPIsTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladislavskiy.ExpandAPIsTask.model.UserInfo;
import com.vladislavskiy.ExpandAPIsTask.service.impl.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserInfoService userInfoService;

    @Test
    public void testIsUserExist() throws Exception {
        String username = "existingUser";

        when(userInfoService.checkUserOnExistByUsername(username)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/isUserExist/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testIsUserNotExist() throws Exception {
        String username = "name1";

        when(userInfoService.checkUserOnExistByUsername(username)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/isUserExist/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testAddNewUser() throws Exception {
        UserInfo newUser = new UserInfo(2,"newUser","user@gmail.com","1234", "USER_ROLE");

        when(userInfoService.addUser(newUser)).thenReturn("User added successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/addNewUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("User added successfully"));
    }
}
