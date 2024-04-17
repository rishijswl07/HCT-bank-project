package com.Bank.project.controller;

import com.Bank.project.entites.*;
import com.Bank.project.service.CustomerService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;
    private CustomerController controller;


    @BeforeEach
    void setUp() {
        customerService = mock(CustomerService.class);
        controller = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerDetails() throws Exception {
        List<cust_Details> mockDetails = new ArrayList<>();
        when(customerService.getCustDetails()).thenReturn(mockDetails);

        mockMvc.perform(get("/bank/getdetails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(mockDetails.size()));
    }

    @Test
    void testGetCustomerAddress() throws Exception {
        List<cust_Address> mockAddress = new ArrayList<>();
        when(customerService.getCustAddress()).thenReturn(mockAddress);
        mockMvc.perform(get("/bank/getaddress"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(mockAddress.size()));
    }


    @AfterEach
    void tearDown() {
    }


    @Test
    void addCustomer()
    {
        CustomerService customerService = mock(CustomerService.class);
        CustomerController controller = new CustomerController(customerService);

        CustomerDetailedAddress custDetAddress = new CustomerDetailedAddress();

        when(customerService.addCustomer(custDetAddress)).thenReturn(custDetAddress);

        ResponseEntity<CustomerDetailedAddress> response = controller.addCustomer(custDetAddress);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(custDetAddress, response.getBody());

    }

    @Test
    void updateCustomer()
    {
        CustomerService customerService = mock(CustomerService.class);
        CustomerController controller = new CustomerController(customerService);
        Long customerId = 123L;
        CustomerDetailedAddress updatedCustomerDetails = new CustomerDetailedAddress();
        CustomerDetailedAddress expectedCustomerDetails = new CustomerDetailedAddress();
        when(customerService.updateCustomer(customerId, updatedCustomerDetails)).thenReturn(expectedCustomerDetails);
        ResponseEntity<CustomerDetailedAddress> responseEntity = controller.updateCustomer(customerId, updatedCustomerDetails);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCustomerDetails, responseEntity.getBody());

    }

    @Test
    void getbalance() throws Exception
    {
        CustomerService customerService = mock(CustomerService.class);
        CustomerController controller = new CustomerController(customerService);
        long accountId = 1L;
        Acc_Balance expectedBalance = new Acc_Balance();
        expectedBalance.setAcc_Id(accountId);
        expectedBalance.setBalance(1000.0);
        when(customerService.getBalance(accountId)).thenReturn(expectedBalance);
        Acc_Balance actualBalance = controller.getbalance(accountId);
        assertNotNull(actualBalance);
        assertEquals(expectedBalance.getAcc_Id(), actualBalance.getAcc_Id());
        assertEquals(expectedBalance.getBalance(), actualBalance.getBalance());
    }

    @Test
    void getBalanceByCustId()throws Exception
    {
        CustomerService customerService = mock(CustomerService.class);
        CustomerController controller = new CustomerController(customerService);
        Long customerId = 1L;
        Acc_Balance expectedBalance = new Acc_Balance();
        expectedBalance.setAcc_Id(customerId);
        expectedBalance.setBalance(1000.0);
        when(customerService.findByCustId(customerId)).thenReturn(expectedBalance);
        ResponseEntity<Acc_Balance> response = controller.getBalanceByCustId(customerId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBalance, response.getBody());

    }

    @Test
    void createTrans() throws Exception {
        CustomerService customerService = mock(CustomerService.class);

        CustomerController controller = new CustomerController(customerService);

        NewTransaction transaction1 = new NewTransaction();
        transaction1.setType("credit");
        when(customerService.createTransaction(transaction1)).thenReturn(ResponseEntity.ok("Success"));
        ResponseEntity<String> response1 = controller.createTrans(transaction1);

        assertNotNull(response1);
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals("Success", response1.getBody());

        NewTransaction transaction2 = new NewTransaction();
        transaction2.setType("debit");
        when(customerService.createTransaction(transaction2)).thenReturn(ResponseEntity.ok("Success"));
        ResponseEntity<String> response2 = controller.createTrans(transaction2);
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals("Success", response2.getBody());

        NewTransaction invalidTransaction = new NewTransaction();
        invalidTransaction.setType("invalidType");
        ResponseEntity<String> response3 = controller.createTrans(invalidTransaction);
        assertNotNull(response3);
        assertEquals(HttpStatus.OK, response3.getStatusCode());
        assertEquals("Error", response3.getBody());
    }


    @Test
    void getbyaccid() throws Exception
    {
        CustomerService customerService = mock(CustomerService.class);
        CustomerController controller = new CustomerController(customerService);
        Acc_Balance accBalance = new Acc_Balance();
        List<Acc_Transactions> expectedTransactions = new ArrayList<>();
        when(customerService.getByAccId(accBalance)).thenReturn(expectedTransactions);
        List<Acc_Transactions> actualTransactions = controller.getbyaccid(accBalance);
        assertNotNull(actualTransactions);
        assertEquals(expectedTransactions.size(), actualTransactions.size());
    }

    @Test
    void getbyrefid() throws Exception
    {
        CustomerService customerService = mock(CustomerService.class);
        CustomerController controller = new CustomerController(customerService);
        Acc_Transactions accTransactions = new Acc_Transactions();
        List<Acc_Transactions> expectedTransactions = new ArrayList<>();
        when(customerService.getByRefId(accTransactions)).thenReturn(expectedTransactions);
        List<Acc_Transactions> actualTransactions = controller.getbyrefid(accTransactions);
        assertNotNull(actualTransactions);
        assertEquals(expectedTransactions.size(), actualTransactions.size());
    }

    @Test
    void getbyrefacc() throws Exception
    {
        CustomerService customerService = mock(CustomerService.class);
        CustomerController controller = new CustomerController(customerService);
        Long transactionRefId = 123L;
        Acc_Balance accBalance = new Acc_Balance();
        Acc_Transactions expectedTransaction = new Acc_Transactions();
        when(customerService.getByRefIdAccId(transactionRefId, accBalance)).thenReturn(expectedTransaction);
        Acc_Transactions actualTransaction = controller.getbyrefacc(transactionRefId, accBalance);
        assertNotNull(actualTransaction);
        assertEquals(expectedTransaction, actualTransaction);
    }
}