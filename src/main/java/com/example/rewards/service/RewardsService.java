package com.example.rewards.service;

import com.example.rewards.constant.Constants;
import com.example.rewards.dao.RewardsDao;
import com.example.rewards.entity.Transaction;
import com.example.rewards.model.Rewards;
import com.example.rewards.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RewardsService {
    private static final Logger logger = LogManager.getLogger(RewardsService.class);

    @Autowired
    private RewardsDao rewardsDao;

    public Optional<Customer> getCustomerById(Integer customerId){
        return rewardsDao.getCustomerById(customerId);
    }

    /*
     * Calculate total reward amount based on List of spending
     * Return total reward amount
     * */
    public long transactionsToRewardPoints(List<Transaction> transactions){
        long totalRewards = 0;
        for(Transaction t: transactions){
            double currentTransactionAmount = t.getTransactionAmount();
            totalRewards += rewardsCalculation(currentTransactionAmount);
        }
        logger.debug("Total Rewards: ", totalRewards);
        return totalRewards;
    }

    /*
     * Calculate rewards based on amount spending
     * Return reward amount
     * */
    public long rewardsCalculation(double transactionAmount){
        if (transactionAmount > Constants.firstRewardLevel && transactionAmount <= Constants.secondRewardLevel){
            return Math.round((transactionAmount - Constants.firstRewardLevel) * Constants.rewardRatio);
        } else if (transactionAmount > Constants.secondRewardLevel){
            return Math.round((transactionAmount - Constants.secondRewardLevel) * Constants.rewardRatio + (transactionAmount - Constants.firstRewardLevel) * Constants.rewardRatio);
        }
        return 0;
    }

    /*
     * Calculate total reward amount based on last 3 months spending
     * Return total reward amount
     * */
    @Transactional
    public Rewards getRewardsByCustomerId(Integer customerId){
        Timestamp lastMonthTimestamp = daysToTimestamp(Constants.daysPerMonth);
        Timestamp lastSecondMonthTimestamp = daysToTimestamp(2 * Constants.daysPerMonth);
        Timestamp lastThirdMonthTimestamp = daysToTimestamp(3 * Constants.daysPerMonth);

        List<Transaction> lastMonthTransaction = rewardsDao.getTransactionByCustomerIdAndDate(customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
        List<Transaction> lastSecondTransaction = rewardsDao.getTransactionByCustomerIdAndDate(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
        List<Transaction> lastThirdMonthTransaction = rewardsDao.getTransactionByCustomerIdAndDate(customerId, lastThirdMonthTimestamp, lastSecondMonthTimestamp);

        long lastMonthRewards = transactionsToRewardPoints(lastMonthTransaction);
        long lastSecondMonthRewards = transactionsToRewardPoints(lastSecondTransaction);
        long lastThirdMonthRewards = transactionsToRewardPoints(lastThirdMonthTransaction);

        long totalRewards = lastMonthRewards + lastSecondMonthRewards + lastThirdMonthRewards;
        Rewards rewards = new Rewards (customerId, lastMonthRewards, lastSecondMonthRewards, lastThirdMonthRewards, totalRewards);
        logger.debug("Rewards: ", rewards);
        return rewards;
    }

    public static Timestamp daysToTimestamp(int days) {
        return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
    }

}
