package com.example.rewards.model;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Rewards {
    private Integer customerId;
    private long lastFirstMonthRewards;
    private long lastSecondMonthRewards;
    private long lastThirdMonthRewards;
    private long totalRewards;
}
