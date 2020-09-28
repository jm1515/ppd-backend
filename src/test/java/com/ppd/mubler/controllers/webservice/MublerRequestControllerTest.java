package com.ppd.mubler.controllers.webservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppd.mubler.MublerApplication;
import com.ppd.mubler.model.entity.MublerRequest;
import com.ppd.mubler.model.entity.User;
import com.ppd.mubler.service.MublerRequestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MublerApplication.class)
@AutoConfigureMockMvc
public class MublerRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MublerRequestService mublerRequestService;

    private User user = new User("test@test.fr", "test", "test", "test", "1234567891", false);

    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtdWJsZXIiLCJpZCI6MCwiZXhwIjoxODc1ODc2OTk0LCJlbWFpbCI6InRlc3RAdGVzdC5mciJ9.2ZJH15n05FSNuMZUluYRoj3tFvO4pWZlnB5WrCvDDJo";

    @Test
    public void saveMublerRequest() throws Exception {

        MublerRequest mublerRequest = new MublerRequest(1.111f, 1.111f, 2.222f, 2.222f, new Date(), 50, false, "L", 0, 0);


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/request")
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mublerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token);

        MvcResult result = mockMvc.perform(requestBuilder)
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn();


    }


}
