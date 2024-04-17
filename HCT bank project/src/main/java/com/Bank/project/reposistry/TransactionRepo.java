package com.Bank.project.reposistry;

import com.Bank.project.entites.Acc_Balance;
import com.Bank.project.entites.Acc_Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Acc_Transactions, Long> {
    List<Acc_Transactions> findByaccBalance(Acc_Balance accBalance);
    List<Acc_Transactions> findBytransactionRefId(Acc_Transactions transactionRefId);
    Optional<Acc_Transactions>findBytransactionRefIdAndAccBalance(long transactionRefId,Acc_Balance accBalance);
}
