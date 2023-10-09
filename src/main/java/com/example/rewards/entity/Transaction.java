package com.example.rewards.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "TRANSACTION")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @Column(name = "TRANSACTION_ID")
    private Integer transactionId;

    @Column(name = "CUSTOMER_ID")
    private Integer customerId;

    @Column(name = "TRANSACTION_DATE")
    private Timestamp transactionDate;

    @Column(name = "TRANSACTION_AMOUNT")
    private double transactionAmount;

}
