package com.example.rewards;

import com.example.rewards.dao.RewardsDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    RewardsDao rewardsDao = new RewardsDao();

    @Test
    public void testTransactionsToRewardPoints() {
        assertEquals(90, rewardsDao.rewardsCalculation(120));
        assertEquals(20, rewardsDao.rewardsCalculation(70));
        assertEquals(210, rewardsDao.rewardsCalculation(180));
        assertEquals(0, rewardsDao.rewardsCalculation(30));
        assertEquals(1850, rewardsDao.rewardsCalculation(1000));
        assertEquals(0, rewardsDao.rewardsCalculation(12.25));
    }

    @Test
    public void testMinimumValue() {
        int min = Integer.MIN_VALUE;
        assertEquals(0, rewardsDao.rewardsCalculation(min));
    }

    @Test
    public void testMaximumValue() {
        int max = Integer.MAX_VALUE;
        assertEquals(4294967144L, rewardsDao.rewardsCalculation(max));
    }

    @Test
    public void testZero() {
        assertEquals(0, rewardsDao.rewardsCalculation(0));
    }

    @Test
    public void testInvalidValue() {
        int invalid = -1;
        assertEquals(0, rewardsDao.rewardsCalculation(-1));
    }

}
