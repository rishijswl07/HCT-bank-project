package com.Bank.project.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class cust_Details
{
    @Id
    private long cust_Id;
    @Column
    private String Name;
    @Column(insertable = false,updatable = false)
    private long Address_Id;
    @Column
    private long Phone;
    @Column
    private String Email;
    @Column
    private Timestamp Created;
    @Column
    private Timestamp LastUpadated;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_Id")
    cust_Address custAddress;
    @OneToOne(mappedBy = "custDetails")
    @JsonBackReference
    private cust_Acc_Map custMap;



}
