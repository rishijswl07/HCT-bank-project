package com.Bank.project.entites;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class CustomerDetailedAddress {
    private String name;
    private long phone;
    private String email;
    private String country;
    private String city;
    private String addresslane;
    private long pin;


}
