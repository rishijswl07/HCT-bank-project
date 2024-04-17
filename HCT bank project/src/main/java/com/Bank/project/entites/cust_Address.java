package com.Bank.project.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@Entity

public class cust_Address
{
    @Id
    private long Address_Id;
    @Column
    private String Country;
    @Column
    private String City;
    @Column
    private String AddressLane;
    @Column
    private long PIN;
    @Column
    private Timestamp LastUpdate;


}
