package com.Bank.project.entites;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewTransaction
{

    private long acc_id;
    private long to_acc_id;
    private String type;
    private double amount;
    }

