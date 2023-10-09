package com.example.rewards;

import com.example.rewards.entity.Customer;

import com.example.rewards.model.Rewards;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RewardsControllerTest.class)
public class RewardsControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void getCustomersByIdApi() throws Exception
    {
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/rewards/customers/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        Customer customer1 = new Customer (1, "John","Doe");
        Customer customer2 = new Customer (2, "Eric","Tran");

        mvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/rewards/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/rewards/customers/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getRewardsByIdApi() throws Exception
    {
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/rewards/rewards/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        Rewards rewards1 = new Rewards(1, 394, 49, 778, 1217);
        Rewards rewards2 = new Rewards(2, 618, 28, 0, 646);

        mvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/rewards/rewards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewards1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/rewards/rewards/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewards2))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
