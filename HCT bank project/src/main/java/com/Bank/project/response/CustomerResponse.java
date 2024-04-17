package com.Bank.project.response;

import lombok.Data;

@Data
public class CustomerResponse {

    private String name;
    private long cus_id;
    private long acc_id;
    private double balance;


}
