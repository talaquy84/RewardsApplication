package com.example.rewards;

import com.example.rewards.entity.Transaction;
import com.example.rewards.service.RewardsService;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    RewardsService rewardsService = new RewardsService();

    @Test
    public void rewardsCalculation() {
        assertEquals(90, rewardsService.rewardsCalculation(120));
        assertEquals(20, rewardsService.rewardsCalculation(70));
        assertEquals(210, rewardsService.rewardsCalculation(180));
        assertEquals(0, rewardsService.rewardsCalculation(30));
        assertEquals(1850, rewardsService.rewardsCalculation(1000));
        assertEquals(0, rewardsService.rewardsCalculation(12.25));
    }


    @Test
    public void testtransactionsToRewardPoints() {
        List<Transaction> transactionList = new ArrayList<>();

        Timestamp time1 = new Timestamp(2023, 10, 03, 0, 0, 0, 0);
        Timestamp time2 = new Timestamp(2023, 9, 10, 0, 0, 0, 0);
        Timestamp time3 = new Timestamp(2023, 9, 11, 0, 0, 0, 0);

        transactionList.add(new Transaction(6, 2, time1, 120.0 ));
        transactionList.add(new Transaction(8, 2, time2, 320.0 ));
        transactionList.add(new Transaction(9, 2, time2, 88.0 ));

        assertEquals(618, rewardsService.transactionsToRewardPoints(transactionList));
    }

    @Test
    public void testMinimumValue() {
        int min = Integer.MIN_VALUE;
        assertEquals(0, rewardsService.rewardsCalculation(min));
    }

    @Test
    public void testMaximumValue() {
        int max = Integer.MAX_VALUE;
        assertEquals(4294967144L, rewardsService.rewardsCalculation(max));
    }

    @Test
    public void testZero() {
        assertEquals(0, rewardsService.rewardsCalculation(0));
    }

    @Test
    public void testInvalidValue() {
        int invalid = -1;
        assertEquals(0, rewardsService.rewardsCalculation(-1));
    }

}
