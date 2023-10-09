package com.example.rewards.dao;

import com.example.rewards.model.Rewards;
import com.example.rewards.entity.Customer;
import com.example.rewards.constant.Constants;
import com.example.rewards.entity.Transaction;
import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RewardsDao {
    private static final Logger logger = LogManager.getLogger(RewardsDao.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Rewards getRewardsByCustomerId(Integer customerId){
        Timestamp lastMonthTimestamp = daysToTimestamp(Constants.daysPerMonth);
        Timestamp lastSecondMonthTimestamp = daysToTimestamp(2 * Constants.daysPerMonth);
        Timestamp lastThirdMonthTimestamp = daysToTimestamp(3 * Constants.daysPerMonth);

        List<Transaction> lastMonthTransaction = getTransactionByCustomerIdAndDate(customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
        List<Transaction> lastSecondTransaction = getTransactionByCustomerIdAndDate(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
        List<Transaction> lastThirdMonthTransaction = getTransactionByCustomerIdAndDate(customerId, lastThirdMonthTimestamp, lastSecondMonthTimestamp);

        long lastMonthRewards = transactionsToRewardPoints(lastMonthTransaction);
        long lastSecondMonthRewards = transactionsToRewardPoints(lastSecondTransaction);
        long lastThirdMonthRewards = transactionsToRewardPoints(lastThirdMonthTransaction);

        long totalRewards = lastMonthRewards + lastSecondMonthRewards + lastThirdMonthRewards;
        Rewards rewards = new Rewards (customerId, lastMonthRewards, lastSecondMonthRewards, lastThirdMonthRewards, totalRewards);
        logger.debug("Rewards: ", rewards);
        return rewards;
    }

    public long transactionsToRewardPoints(List<Transaction> transactions){
        long totalRewards = 0;
        for(Transaction t: transactions){
            double currentTransactionAmount = t.getTransactionAmount();
            totalRewards += rewardsCalculation(currentTransactionAmount);
        }
        logger.debug("Total Rewards: ", totalRewards);
        return totalRewards;
    }

    public long rewardsCalculation(double transactionAmount){
        if (transactionAmount > Constants.firstRewardLevel && transactionAmount <= Constants.secondRewardLevel){
            return Math.round((transactionAmount - Constants.firstRewardLevel) * Constants.rewardRatio);
        } else if (transactionAmount > Constants.secondRewardLevel){
            return Math.round((transactionAmount - Constants.secondRewardLevel) * Constants.rewardRatio + (transactionAmount - Constants.firstRewardLevel) * Constants.rewardRatio);
        }
        return 0;
    }

    public List<Transaction> getTransactionByCustomerIdAndDate(Integer customerId, Timestamp startDate, Timestamp endDate){
       return  transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);
    }

    public static Timestamp daysToTimestamp(int days) {
        return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
    }

    public Customer getCustomerById(Integer customerId){
        Customer customer = customerRepository.findByCustomerId(customerId);
        logger.debug("Customer: ", customer);
        return customer;
    }
}
