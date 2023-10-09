package com.example.rewards.service;

import com.example.rewards.dao.RewardsDao;
import com.example.rewards.model.Rewards;
import com.example.rewards.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RewardsService {
    private RewardsDao rewardsDao;

    @Autowired
    public RewardsService(RewardsDao rewardsDao){
        this.rewardsDao = rewardsDao;
    }

    public Optional<Customer> getCustomerById(Integer customerId){
        return rewardsDao.getCustomerById(customerId);
    }

    @Transactional
    public Rewards getRewardsByCustomerId(Integer customerId){
        return rewardsDao.getRewardsByCustomerId(customerId);
    }
}
