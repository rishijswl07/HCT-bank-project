package com.Bank.project.controller;


import com.Bank.project.Exception.InvalidException;
import com.Bank.project.entites.*;
import com.Bank.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("bank")


public class CustomerController {
    @Autowired
    CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }
    @GetMapping("getdetails")
    public List<cust_Details> getCustomerDetails(){
        List<cust_Details> custDetails=customerService.getCustDetails();
           return customerService.getCustDetails();
    }
    @GetMapping("getaddress")
    public List<cust_Address> getCustomerAddress(){
        return customerService.getCustAddress();
    }
    @PostMapping("post")
    public ResponseEntity<CustomerDetailedAddress> addCustomer(@RequestBody CustomerDetailedAddress custDetAddress){
        return new ResponseEntity<>(customerService.addCustomer(custDetAddress), HttpStatus.CREATED);
    }
    @PutMapping("put/{id}")
    public ResponseEntity<CustomerDetailedAddress> updateCustomer(@PathVariable("id") Long cust_Id, @RequestBody CustomerDetailedAddress custDetAddress) {
        return new ResponseEntity<>(customerService.updateCustomer(cust_Id, custDetAddress), HttpStatus.OK);
    }


    @GetMapping("getbalance/{id}")
    public Acc_Balance getbalance(@PathVariable("id") long acc_Id){
        return customerService.getBalance(acc_Id);

    }

    @GetMapping("balance/{cust_Id}")
    public ResponseEntity<Acc_Balance> getBalanceByCustId(@PathVariable("cust_Id") Long cust_Id) throws InvalidException {
        Acc_Balance balance = customerService.findByCustId(cust_Id);
        if (balance != null) {
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping("trans")
    public ResponseEntity<String> createTrans(@RequestBody NewTransaction transaction) throws InvalidException {
        String type = transaction.getType();
        if(type.equals("credit")){
            return customerService.createTransaction(transaction);
        }
       else if(type.equals("debit"))
        {
            return customerService.createTransaction(transaction);
        }
        else{
            return ResponseEntity.ok("Error");
        }

    }

   @GetMapping("acc")
    public List<Acc_Transactions> getbyaccid(@RequestParam("accid") Acc_Balance accBalance){
       return customerService.getByAccId(accBalance);    }
    @GetMapping("refid")
    public List<Acc_Transactions>getbyrefid(@RequestBody Acc_Transactions accTransactions)
    {
        return customerService.getByRefId(accTransactions);
    }



      @GetMapping("refacc")
      public Acc_Transactions getbyrefacc(@RequestParam("refid") Long transactionRefId, @RequestParam ("accid") Acc_Balance accBalance){
        return customerService.getByRefIdAccId(transactionRefId,accBalance);
   }
}