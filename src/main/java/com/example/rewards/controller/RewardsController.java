package com.example.rewards.controller;

import com.example.rewards.model.Rewards;
import com.example.rewards.entity.Customer;
import com.example.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("rewards")
public class RewardsController {
    private RewardsService rewardsService;

    @Autowired
    public RewardsController(RewardsService rewardsService){
        this.rewardsService = rewardsService;
    }

    /*
     * GET method
     * Get customer info by customer ID
     * @params Integer customerId
     * */
    @GetMapping(value = "/customers/{customerId}", produces = "application/json")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer customerId){
        Optional<Customer> customer = rewardsService.getCustomerById(customerId);
        if(!customer.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        return ResponseEntity.ok().body(customer);
    }

    /*
    * GET method
    * Get rewards by customer ID
    * @params Integer customerId
    * */
    @GetMapping(value ="/rewards/{customerId}", produces = "application/json")
    public ResponseEntity<?> getRewardsById(@PathVariable Integer customerId){
        Rewards rewards = rewardsService.getRewardsByCustomerId(customerId);
        Optional<Customer> customer = rewardsService.getCustomerById(customerId);
        if(!customer.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        return ResponseEntity.ok().body(rewards);
    }
}
