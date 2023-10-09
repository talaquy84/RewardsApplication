package com.example.rewards.dao;

import com.example.rewards.entity.Customer;
import com.example.rewards.entity.Transaction;
import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class RewardsDao {
    private static final Logger logger = LogManager.getLogger(RewardsDao.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Transaction> getTransactionByCustomerIdAndDate(Integer customerId, Timestamp startDate, Timestamp endDate){
       return  transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);
    }

    public Optional<Customer> getCustomerById(Integer customerId){
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findByCustomerId(customerId));
        logger.debug("Customer: ", customer);
        return customer;
    }
}
