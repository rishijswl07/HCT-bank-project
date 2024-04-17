package com.Bank.project.reposistry;
;
import com.Bank.project.entites.cust_Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cust_AddressRepo extends JpaRepository<cust_Address,Integer> {

}
