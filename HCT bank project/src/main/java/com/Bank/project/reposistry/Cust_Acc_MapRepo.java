package com.Bank.project.reposistry;

import com.Bank.project.entites.cust_Acc_Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Cust_Acc_MapRepo extends JpaRepository<cust_Acc_Map,Long> {


}
