package com.Bank.project.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cust_Acc_Map")
public class cust_Acc_Map
{
    @Id
    private long Acc_Id;
    @Column(insertable = false,updatable = false)
    private long cust_Id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cust_Id")
     cust_Details custDetails;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accId")
     Acc_Balance Accbalance;

    public cust_Acc_Map()
    {

    }




}
