package com.Bank.project.reposistry;

import com.Bank.project.entites.cust_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<cust_Details,Long>
{

}
