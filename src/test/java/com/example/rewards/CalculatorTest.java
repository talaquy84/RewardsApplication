package com.example.rewards;

import com.example.rewards.service.RewardsService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    RewardsService rewardsService = new RewardsService();

    @Test
    public void testTransactionsToRewardPoints() {
        assertEquals(90, rewardsService.rewardsCalculation(120));
        assertEquals(20, rewardsService.rewardsCalculation(70));
        assertEquals(210, rewardsService.rewardsCalculation(180));
        assertEquals(0, rewardsService.rewardsCalculation(30));
        assertEquals(1850, rewardsService.rewardsCalculation(1000));
        assertEquals(0, rewardsService.rewardsCalculation(12.25));
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
