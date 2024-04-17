package com.Bank.project.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "Acc_Transactions")
public class Acc_Transactions
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    @Column(name = "transactionRefId")
    private long transactionRefId;
    private long Acc_Id;
    private double Credit;
    private double Debit;
    private double AvlBalance;
    private Timestamp LastUpdated;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="accountId",referencedColumnName = "acc_Id")
    @JsonBackReference
    Acc_Balance accBalance;

    public Acc_Transactions()
    {

    }
}
