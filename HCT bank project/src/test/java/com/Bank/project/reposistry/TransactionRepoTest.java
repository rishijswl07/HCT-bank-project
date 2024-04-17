package com.Bank.project.reposistry;

import com.Bank.project.entites.Acc_Balance;
import com.Bank.project.entites.Acc_Transactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionRepoTest {
    @Mock
    private Acc_Balance accBalance;

    @Mock
    private TransactionRepo transactionRepo;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByAccBalance() {
        // Arrange
        Acc_Transactions transaction1 = new Acc_Transactions();
        Acc_Transactions transaction2 = new Acc_Transactions();
        List<Acc_Transactions> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepo.findByaccBalance(accBalance)).thenReturn(transactions);

        // Act
        List<Acc_Transactions> result = transactionRepo.findByaccBalance(accBalance);

        // Assert
        assertEquals(transactions.size(), result.size());
        verify(transactionRepo, times(1)).findByaccBalance(accBalance);
    }

    @Test
    void testFindByTransactionRefId() {
        // Arrange
        Acc_Transactions transaction1 = new Acc_Transactions();
        Acc_Transactions transaction2 = new Acc_Transactions();
        List<Acc_Transactions> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepo.findBytransactionRefId(any())).thenReturn(transactions);

        // Act
        List<Acc_Transactions> result = transactionRepo.findBytransactionRefId(any());

        // Assert
        assertEquals(transactions.size(), result.size());
        verify(transactionRepo, times(1)).findBytransactionRefId(any());
    }

    @Test
    void testFindByTransactionRefIdAndAccBalance() {
        // Arrange
        long transactionRefId = 1L;
        Acc_Transactions transaction = new Acc_Transactions();
        when(transactionRepo.findBytransactionRefIdAndAccBalance(transactionRefId, accBalance))
                .thenReturn(Optional.of(transaction));

        // Act
        Optional<Acc_Transactions> result = transactionRepo.findBytransactionRefIdAndAccBalance(transactionRefId, accBalance);

        // Assert
        assertEquals(transaction, result.orElse(null));
        verify(transactionRepo, times(1)).findBytransactionRefIdAndAccBalance(transactionRefId, accBalance);
    }



    @AfterEach
    void tearDown() {

    }

}