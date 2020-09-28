package com.ppd.mubler.controllers.webservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppd.mubler.MublerApplication;
import com.ppd.mubler.model.entity.User;
import com.ppd.mubler.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MublerApplication.class)
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user = new User("test@test.fr", "test", "test", "test", "1234567891", false);

    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtdWJsZXIiLCJpZCI6MCwiZXhwIjoxODc1ODc2OTk0LCJlbWFpbCI6InRlc3RAdGVzdC5mciJ9.2ZJH15n05FSNuMZUluYRoj3tFvO4pWZlnB5WrCvDDJo";

    @Test
    public void connectTest() throws Exception {

        //SecurityToken securityToken = new SecurityToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtdWJsZXIiLCJpZCI6MCwiZXhwIjoxODc1ODc2OTk0LCJlbWFpbCI6InRlc3RAdGVzdC5mciJ9.2ZJH15n05FSNuMZUluYRoj3tFvO4pWZlnB5WrCvDDJo");

        Mockito.when(userService.getUserByEmailAndPassword(
                Mockito.anyString(),
                Mockito.anyString()))
                .thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        //MockHttpServletResponse response = result.getResponse();

    }

    @Test
    public void verifyTest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/verify")
                .accept(MediaType.APPLICATION_JSON)
                .content(token)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

    }

}
