package com.Bank.project.reposistry;

import com.Bank.project.entites.Acc_Balance;
import com.Bank.project.entites.cust_Acc_Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Acc_BalanceRepo extends JpaRepository<Acc_Balance,Long> {



}
