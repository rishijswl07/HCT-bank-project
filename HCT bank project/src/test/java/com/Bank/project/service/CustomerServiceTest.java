package com.Bank.project.service;

import com.Bank.project.Exception.InvalidException;
import com.Bank.project.entites.*;
import com.Bank.project.reposistry.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CustomerServiceTest {
    @Mock
    private Acc_BalanceRepo accBalanceRepo;

    @Mock
    private Cust_Acc_MapRepo custMapRepo;

    @Mock
    private TransactionRepo accTransRepo;

    @Mock
    private cust_AddressRepo addressRepo;

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reset(accBalanceRepo, custMapRepo, accTransRepo, addressRepo, customerRepo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAddCustomer() {
        CustomerDetailedAddress custDetAddress = new CustomerDetailedAddress();
        custDetAddress.setName("Ajay");
        custDetAddress.setPhone(1234567890);
        custDetAddress.setEmail("ajai123@gmail.com");
        custDetAddress.setCity("Indore");
        custDetAddress.setPin(12345);
        custDetAddress.setCountry("India");
        custDetAddress.setAddresslane("Indore");

        when(addressRepo.save(any(cust_Address.class))).thenReturn(new cust_Address());
        when(customerRepo.save(any(cust_Details.class))).thenReturn(new cust_Details());
        when(accBalanceRepo.save(any(Acc_Balance.class))).thenReturn(new Acc_Balance());
        when(custMapRepo.save(any(cust_Acc_Map.class))).thenReturn(new cust_Acc_Map());

        CustomerDetailedAddress result = customerService.addCustomer(custDetAddress);

        verify(addressRepo, times(1)).save(any(cust_Address.class));
        verify(customerRepo, times(1)).save(any(cust_Details.class));
        verify(accBalanceRepo, times(1)).save(any(Acc_Balance.class));
        verify(custMapRepo, times(1)).save(any(cust_Acc_Map.class));

        assertNotNull(result);
    }

    @Test
    void testgetCustDetails()
    {
        cust_Details customer1 = new cust_Details();
        customer1.setCust_Id(1L);
        customer1.setName("new");
        customer1.setEmail("new@gmail.com");
        customer1.setPhone(3333333);
        customer1.setCreated(new Timestamp(new Date().getTime()));
        customer1.setLastUpadated(new Timestamp(new Date().getTime()));
        List<cust_Details> mockCustomers = new ArrayList<>();
        mockCustomers.add(customer1);
        when(customerRepo.findAll()).thenReturn(mockCustomers);
        List<cust_Details> result = customerService.getCustDetails();
        assertEquals(mockCustomers.size(), result.size());
        for (int i = 0; i < mockCustomers.size(); i++) {
            cust_Details mockCustomer = mockCustomers.get(i);
            cust_Details actualCustomer = result.get(i);
            assertEquals(mockCustomer.getCust_Id(), actualCustomer.getCust_Id());
            assertEquals(mockCustomer.getName(), actualCustomer.getName());
            assertEquals(mockCustomer.getEmail(), actualCustomer.getEmail());
            assertEquals(mockCustomer.getPhone(), actualCustomer.getPhone());
            assertEquals(mockCustomer.getCreated(), actualCustomer.getCreated());
            assertEquals(mockCustomer.getLastUpadated(), actualCustomer.getLastUpadated());
        }
    }

    @Test
    void testgetCustAddress()
    {
        cust_Address addr = new cust_Address();
        addr.setAddress_Id(22);
        addr.setPIN(54544);
        addr.setAddressLane("yyyy");
        addr.setCity("HYD");
        addr.setCountry("IND");
        addr.setLastUpdate(new Timestamp(new Date().getTime()));
        List<cust_Address> custm=new ArrayList<>();
        custm.add(addr);
        when(addressRepo.findAll()).thenReturn(custm);
        List<cust_Address> result=customerService.getCustAddress();
        assertEquals(custm.size(),result.size());
        for(int i=0;i<custm.size();i++)
        {
            cust_Address mockcust=custm.get(i);
            cust_Address maincust=result.get(i);
            assertEquals(mockcust.getAddress_Id(),maincust.getAddress_Id());
            assertEquals(mockcust.getAddressLane(),maincust.getAddressLane());
            assertEquals(mockcust.getCity(),maincust.getCity());
            assertEquals(mockcust.getPIN(),maincust.getPIN());
            assertEquals(mockcust.getCountry(),maincust.getCountry());
            assertEquals(mockcust.getLastUpdate(),maincust.getLastUpdate());

        }

    }

    @Test
    void testupdateCustomer()
    {
        Long cust_Id = 1L;
        CustomerDetailedAddress custDetAddress = new CustomerDetailedAddress();
        custDetAddress.setName("new");
        custDetAddress.setPhone(1234567890);
        custDetAddress.setEmail("new12@gmail.com");
        custDetAddress.setCountry("USA");
        custDetAddress.setCity("New York");
        custDetAddress.setAddresslane("123 Main");
        custDetAddress.setPin(12345);
        cust_Details custDetails = new cust_Details();
        custDetails.setCust_Id(cust_Id);
        custDetails.setName("Old Name");
        custDetails.setPhone(1234567890);
        custDetails.setEmail("Old Email");
        cust_Address custAddress = new cust_Address();
        custAddress.setCountry("Old Country");
        custAddress.setCity("Old City");
        custAddress.setAddressLane("Old Address Lane");
        custAddress.setPIN(12345);
        custDetails.setCustAddress(custAddress);
        when(customerRepo.findById(cust_Id)).thenReturn(Optional.of(custDetails));
        when(customerRepo.save(any(cust_Details.class))).thenReturn(custDetails);
        when(addressRepo.save(any(cust_Address.class))).thenReturn(custAddress);
        CustomerDetailedAddress result = customerService.updateCustomer(cust_Id, custDetAddress);
        assertEquals(custDetAddress, result);
        assertEquals(custDetAddress.getName(), custDetails.getName());
        assertEquals(custDetAddress.getPhone(), custDetails.getPhone());
        assertEquals(custDetAddress.getEmail(), custDetails.getEmail());
        assertEquals(custDetAddress.getCountry(), custAddress.getCountry());
        assertEquals(custDetAddress.getCity(), custAddress.getCity());
        assertEquals(custDetAddress.getAddresslane(), custAddress.getAddressLane());
        assertEquals(custDetAddress.getPin(), custAddress.getPIN());


    }

    @Test
    void getBalance()
    {
        long acc_Id = 1L;
        Acc_Balance accBalance = new Acc_Balance();
        accBalance.setAcc_Id(acc_Id);
        accBalance.setBalance(500.0);

        when(accBalanceRepo.findById(acc_Id)).thenReturn(Optional.of(accBalance));

        Acc_Balance result = customerService.getBalance(acc_Id);

        assertEquals(accBalance, result);
        assertEquals(accBalance.getBalance(), result.getBalance());



    }

    @Test
    void findByCustId()
    {
        Long cust_Id = 1L;
        cust_Details custDetails = new cust_Details();
        custDetails.setCust_Id(cust_Id);
        cust_Acc_Map custMap = new cust_Acc_Map();
        Acc_Balance accBalance = new Acc_Balance();
        accBalance.setBalance(500.0);
        custMap.setAccbalance(accBalance);
        custDetails.setCustMap(custMap);
        when(customerRepo.findById(cust_Id)).thenReturn(Optional.of(custDetails));
        Acc_Balance result = customerService.findByCustId(cust_Id);
        assertEquals(accBalance, result);

    }

    @Test
    void testCreateTransactionSufficientBalance() throws InvalidException {
        NewTransaction newTransaction = new NewTransaction();
        newTransaction.setAcc_id(1L);
        newTransaction.setTo_acc_id(2L);
        newTransaction.setAmount(100.0);
        Acc_Balance senderBalance = new Acc_Balance();
        senderBalance.setAcc_Id(1L);
        senderBalance.setBalance(500.0);
        when(accBalanceRepo.findById(1L)).thenReturn(Optional.of(senderBalance));
        Acc_Balance receiverBalance = new Acc_Balance();
        receiverBalance.setAcc_Id(2L);
        receiverBalance.setBalance(500.0);
        when(accBalanceRepo.findById(2L)).thenReturn(Optional.of(receiverBalance));
        ResponseEntity<String> responseEntity = customerService.createTransaction(newTransaction);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Transaction Successfully", responseEntity.getBody());
    }

    @Test
    void testCreateTransaction() throws InvalidException {
        NewTransaction sufficientTransaction = new NewTransaction();
        sufficientTransaction.setAcc_id(1L);
        sufficientTransaction.setTo_acc_id(2L);
        sufficientTransaction.setAmount(100.0);
        Acc_Balance senderBalance = new Acc_Balance();
        senderBalance.setAcc_Id(1L);
        senderBalance.setBalance(200.0);
        Acc_Balance receiverBalance = new Acc_Balance();
        receiverBalance.setAcc_Id(2L);
        receiverBalance.setBalance(100.0);
        when(accBalanceRepo.findById(1L)).thenReturn(Optional.of(senderBalance));
        when(accBalanceRepo.findById(2L)).thenReturn(Optional.of(receiverBalance));

        ResponseEntity<String> sufficientResponse = customerService.createTransaction(sufficientTransaction);

        assertEquals(HttpStatus.OK, sufficientResponse.getStatusCode());
        assertEquals("Transaction Successfully", sufficientResponse.getBody());
        verify(accBalanceRepo, times(1)).findById(1L);
        verify(accBalanceRepo, times(1)).findById(2L);
        verify(accTransRepo, times(2)).save(any(Acc_Transactions.class));

        when(accBalanceRepo.findById(2L)).thenReturn(Optional.empty());

        NewTransaction insufficientTransaction = new NewTransaction();
        insufficientTransaction.setAcc_id(1L);
        insufficientTransaction.setTo_acc_id(2L);
        insufficientTransaction.setAmount(300.0);
        when(accBalanceRepo.findById(1L)).thenReturn(Optional.of(senderBalance));

        ResponseEntity<String> insufficientResponse = customerService.createTransaction(insufficientTransaction);

        assertEquals(HttpStatus.NOT_FOUND, insufficientResponse.getStatusCode());
        assertTrue(insufficientResponse.getBody().contains("Balance cannot be debited"));
        assertTrue(insufficientResponse.getBody().contains("HCT400"));

    }

    @Test
    void getByAccId()
    {
        Acc_Balance accBalance = new Acc_Balance();
        accBalance.setAcc_Id(11);
        Acc_Transactions transaction1 = new Acc_Transactions();
        transaction1.setTransactionId(11);
        transaction1.setTransactionRefId(123);
        List<Acc_Transactions> transactions = new ArrayList<>();
        transactions.add(transaction1);
        when(accTransRepo.findByaccBalance(accBalance)).thenReturn(transactions);
        List<Acc_Transactions> result = customerService.getByAccId(accBalance);
       assertEquals(transactions.size(), result.size());
   }

    @Test
    void testGetByRefId() {

        Acc_Transactions transactionRefId = new Acc_Transactions();
        transactionRefId.setTransactionRefId(123L);
        List<Acc_Transactions> mockTransactions = new ArrayList<>();
        mockTransactions.add(transactionRefId);
        when(accTransRepo.findBytransactionRefId(any())).thenReturn(mockTransactions);
        List<Acc_Transactions> result = customerService.getByRefId(transactionRefId);
        assertEquals(mockTransactions.size(), result.size());
    }

@Test
void testGetByRefIdAccId() {
    Long transRefId = 123L;
    Acc_Balance accBalance = new Acc_Balance();
    accBalance.setAcc_Id(456L);
    Acc_Transactions expectedTransaction = new Acc_Transactions();

    when(accTransRepo.findBytransactionRefIdAndAccBalance(anyLong(), any(Acc_Balance.class)))
            .thenReturn(Optional.of(expectedTransaction));

    Acc_Transactions result = customerService.getByRefIdAccId(transRefId, accBalance);

    assertEquals(expectedTransaction, result);
}

}